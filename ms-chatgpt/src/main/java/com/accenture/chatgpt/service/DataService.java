package com.accenture.chatgpt.service;

import com.accenture.chatgpt.client.BillingFeignClient;
import com.accenture.chatgpt.dto.AnswerDTO;
import com.accenture.chatgpt.dto.BillingAddRequestDTO;
import com.accenture.chatgpt.dto.DataDTO;
import com.accenture.chatgpt.dto.MessageRequestDTO;
import com.accenture.chatgpt.dto.MessageResponseDTO;
import com.accenture.chatgpt.dto.UserDataDTO;
import com.accenture.chatgpt.model.Answer;
import com.accenture.chatgpt.model.Data;
import com.accenture.chatgpt.repository.AnswerRepository;
import com.accenture.chatgpt.repository.DataRepository;
import com.accenture.chatgpt.util.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DataService extends BaseService {

    private final BillingFeignClient billingFeignClient;
    private final DataRepository dataRepository;
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    public DataService(BillingFeignClient billingFeignClient,
                       DataRepository dataRepository,
                       AnswerRepository answerRepository,
                       ModelMapper modelMapper) {
        this.billingFeignClient = billingFeignClient;
        this.dataRepository = dataRepository;
        this.answerRepository = answerRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Mono<DataDTO> addData(DataDTO dataDTO, String token) {
        return Mono.just(dataDTO).doOnNext(dto -> {
            Data data = modelMapper.map(dto, Data.class);
            Optional<Data> dataOptional = dataRepository.findByQuestion(data.getQuestion());
            if (dataOptional.isPresent()) {
                throw new IllegalArgumentException(String.format("error - question '%s' already exists, cannot created response answer", data.getQuestion()));
            }

            UUID uuid = UUID.randomUUID();
            String username = getNameUserFromToken(token);

            data.setCreatedAt(LocalDateTime.now());
            data.setUsername(username);
            data.setTimesQuestionWasAsked(0);
            data.setUuid(uuid);

            Answer answer = answerRepository.findByValue(data.getAnswer().getValue())
                    .orElseGet(() -> {
                        Answer newAnswer = new Answer();
                        newAnswer.setValue(data.getAnswer().getValue());
                        answerRepository.save(newAnswer);
                        data.setAnswer(newAnswer);
                        return newAnswer;
                    });
            data.setAnswer(answer);
            answer.addData(data);
            dataRepository.save(data);
        });
    }

    @Transactional(readOnly = true)
    public Flux<DataDTO> getAllData() {
        return Flux.fromIterable(mapDataList());
    }

    @Transactional(readOnly = true)
    public Mono<MessageResponseDTO> getAnswer(MessageRequestDTO messageRequestDTO, String token) {
        AnswerDTO answer = new AnswerDTO();
        answer.setValue("No cuento con una respuesta para tu pregunta en este momento");
        String username = getNameUserFromToken(token);
        return Flux.fromIterable(mapDataList())
                .filter(dataDTO -> dataDTO.getQuestion().toLowerCase(Locale.ROOT).equals(messageRequestDTO.getQuestion().toLowerCase()))
                .switchIfEmpty(Flux.defer(() -> Flux.just(new DataDTO("", answer))))
                .elementAt(0).map(dataDTO -> {
                    if (dataDTO.getId() == null) {
                        return new MessageResponseDTO(answer.getValue());
                    }

                    Data data = dataRepository.findById(dataDTO.getId())
                            .orElseThrow(() -> new NoSuchElementException("error - no data was found"));
                    data.setTimesQuestionWasAsked(dataDTO.getTimesQuestionWasAsked());
                    dataRepository.save(data);

                    billingFeignClient.addBilling(new BillingAddRequestDTO(username, data.getUuid()));
                    return new MessageResponseDTO(StringUtil.replaceText(dataDTO.getAnswer().getValue(), username));
                });
    }

    @Transactional(readOnly = true)
    public Mono<Integer> getDataTimeAsked(MessageRequestDTO messageRequestDTO) {
        Data data = dataRepository.findByQuestion(messageRequestDTO.getQuestion())
                .orElseThrow(() -> new NoSuchElementException(String.format("error - no data found for question '%s'", messageRequestDTO.getQuestion())));
        return Mono.just(data.getTimesQuestionWasAsked());
    }

    @Transactional(readOnly = true)
    public Mono<List<String>> getDataNotAsked() {
        List<String> list = new ArrayList<>();
        return Mono.just(list).map(l -> dataRepository.findAll()
                .stream()
                .filter(data -> data.getTimesQuestionWasAsked() == 0)
                .map(Data::getQuestion)
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public List<UserDataDTO> getAdminsAskedOwnQuestions() {
        List<Data> data = dataRepository.findAll();

        Map<String, String> questionsMap = getUUIDQuestionMap(data);

        Map<String, List<String>> questionsByUser = data.stream()
                .collect(Collectors.groupingBy(
                        Data::getUsername,
                        Collectors.mapping(d -> d.getUuid().toString(), Collectors.toList())));

        return questionsByUser.entrySet()
                .stream()
                .map(entry -> new UserDataDTO(entry.getKey(), entry.getValue(), questionsMap))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<String, String> getQuestionsByDates() {
        List<Data> data = dataRepository.findAll();
        return getUUIDQuestionMap(data);
    }

    private List<DataDTO> mapDataList() {
        return dataRepository.findAll()
                .stream()
                .map(data -> {
                    DataDTO dataDTO = modelMapper
                            .map(data, DataDTO.class);
                    Integer timesQuestionWasAsked = dataDTO.getTimesQuestionWasAsked();
                    timesQuestionWasAsked++;
                    dataDTO.setTimesQuestionWasAsked(timesQuestionWasAsked);
                    return dataDTO;
                })
                .collect(Collectors.toList());
    }

    private Map<String, String> getUUIDQuestionMap(List<Data> data) {
        return data.stream()
                .collect(Collectors.toMap(
                        d -> d.getUuid().toString(),
                        Data::getQuestion));
    }
}

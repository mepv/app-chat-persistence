package com.accenture.chatgpt.config;

import com.accenture.chatgpt.model.Answer;
import com.accenture.chatgpt.model.Data;
import com.accenture.chatgpt.repository.AnswerRepository;
import com.accenture.chatgpt.repository.DataRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class DataConfig {

    private final DataRepository dataRepository;
    private final AnswerRepository answerRepository;

    public DataConfig(DataRepository dataRepository, AnswerRepository answerRepository) {
        this.dataRepository = dataRepository;
        this.answerRepository = answerRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            UUID uuidA = UUID.fromString("71b2d8a3-32e6-43f3-85fd-a5541037f161");
            UUID uuidB = UUID.fromString("4da8f02f-6373-42a9-a1fc-0fcb172f29a1");
            Optional<Data> dataOptionalA = dataRepository.findByUuid(uuidA);
            Optional<Data> dataOptionalB = dataRepository.findByUuid(uuidB);
            if (!dataOptionalA.isPresent() && !dataOptionalB.isPresent()) {
                Data questionA = new Data();
                questionA.setUuid(uuidA);
                questionA.setQuestion("¿Cuál es la capital de Argentina?");
                questionA.setTimesQuestionWasAsked(0);
                questionA.setUsername("admin");
                questionA.setCreatedAt(LocalDateTime.now());
                Answer answerA = new Answer();
                answerA.setValue("La capital de Argentina es Buenos Aires, también conocida como Ciudad Autónoma de Buenos Aires");
                answerRepository.save(answerA);
                questionA.setAnswer(answerA);
                answerA.addData(questionA);
                dataRepository.save(questionA);

                Data questionB = new Data();
                questionB.setUuid(uuidB);
                questionB.setQuestion("¿Dónde queda Bariloche?");
                questionB.setTimesQuestionWasAsked(0);
                questionB.setUsername("admin");
                questionB.setCreatedAt(LocalDateTime.now());
                Answer answerB = new Answer();
                answerB.setValue("San Carlos de Bariloche se encuentra en la región patagónica de Argentina, es una de las ciudades más atractivas de Argentina");
                answerRepository.save(answerB);
                questionB.setAnswer(answerB);
                answerB.addData(questionB);
                dataRepository.save(questionB);
            }
        };
    }
}

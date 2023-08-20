package com.accenture.chatgpt.service;

import com.accenture.chatgpt.client.BillingUserFeignClient;
import com.accenture.chatgpt.dto.BillingAddResponseDTO;
import com.accenture.chatgpt.dto.BillingResponseDTO;
import com.accenture.chatgpt.dto.QueryDTO;
import com.accenture.chatgpt.dto.UserDTO;
import com.accenture.chatgpt.dto.UserDataDTO;
import com.accenture.chatgpt.model.Billing;
import com.accenture.chatgpt.model.Currency;
import com.accenture.chatgpt.repository.BillingRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BillingService extends BaseService {

    private static final Integer MAX_QUERY_FREE = 3;

    @Value("${billing.query.value}")
    private BigDecimal billingQueryValue;
    private final BillingRepository billingRepository;
    private final BillingUserFeignClient billingUserFeignClient;

    public BillingService(BillingRepository billingRepository,
                          BillingUserFeignClient billingUserFeignClient) {
        this.billingRepository = billingRepository;
        this.billingUserFeignClient = billingUserFeignClient;
    }

    /**
     * Add query count
     *
     * @param username
     * @return
     */
    @Transactional
    public Mono<BillingAddResponseDTO> add(String username, UUID questionUUID) {
        return Mono.fromRunnable(() -> {
            Billing billing = billingRepository.findByQuestionUUIDAndUsername(questionUUID, username)
                    .orElseGet(() -> {
                        Billing newBilling = new Billing();
                        newBilling.setUsername(username);
                        newBilling.setCurrency(Currency.PESO.getValue());
                        newBilling.setQuestionUUID(questionUUID);
                        newBilling.setAskedAt(LocalDateTime.now());
                        return newBilling;
                    });
            billingRepository.save(billing);
            billingRepository.incrementQueryByQuestionUUIDAndUsername(questionUUID, username);
        });
    }

    /**
     * Returns billing
     *
     * @param username
     * @return
     */
    @Transactional(readOnly = true)
    public Mono<BillingResponseDTO> getBilling(String username) {
        return Mono.just(username)
                .map(user -> {
                    try {
                        UserDTO userDTO = billingUserFeignClient.getUsername(user);
                        QueryDTO queryDTO = getQuery(user);
                        int query = queryDTO.getValue();
                        return new BillingResponseDTO(userDTO.getFirstName(),
                                userDTO.getLastName(),
                                billingQueryValue.multiply(new BigDecimal(query)),
                                queryDTO.getCurrency());
                    } catch (FeignException e) {
                        throw new NoSuchElementException(String.format("error - username '%s' not found", username));
                    }
                });
    }

    /**
     * Set value billing
     *
     * @param value
     * @return
     */
    public Mono<BigDecimal> setValueBilling(BigDecimal value) {
        return Mono.just(value).doOnNext(v -> billingQueryValue = v);
    }

    @Transactional(readOnly = true)
    public Mono<List<String>> getUsersWithoutDebt() {
        List<String> billings = new ArrayList<>();
        return Mono.just(billings)
                .map(l -> billingRepository.findAll()
                        .stream()
                        .map(Billing::getUsername)
                        .filter(username -> getQuery(username).getValue() == 0)
                        .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public List<UserDataDTO> adminsAskedOwnQuestions() {
        List<Billing> billings = billingRepository.findAll();

        Map<String, List<String>> questionsByUser = billings
                .stream()
                .collect(Collectors.groupingBy(
                        Billing::getUsername,
                        Collectors.mapping(billing -> billing.getQuestionUUID().toString(), Collectors.toList())));

        return questionsByUser.entrySet()
                .stream()
                .map(entry -> new UserDataDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDataDTO> getQuestionsByDates(LocalDateTime startDate, LocalDateTime endDate) {
        List<Billing> billings = billingRepository.findByAskedAtBetween(startDate, endDate);
        if (billings.isEmpty())
            throw new NoSuchElementException();
        Map<String, List<String>> questionsByUser = billings
                .stream()
                .collect(Collectors.groupingBy(
                        Billing::getUsername,
                        Collectors.mapping(
                                billing -> billing.getQuestionUUID().toString()
                                        .concat("/")
                                        .concat(billing.getAskedAt().toString()),
                                Collectors.toList())));
        return questionsByUser.entrySet()
                .stream()
                .map(entry -> new UserDataDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private QueryDTO getQuery(String username) {
        List<Billing> billings = billingRepository.findAllByUsername(username);
        if (billings.isEmpty())
            throw new NoSuchElementException(String.format("there is no billing information for username '%s'", username));
        List<String> currency = billings.stream()
                .flatMap(billing -> Stream.of(billing.getCurrency())).collect(Collectors.toList());
        int query = billings.stream()
                .mapToInt(Billing::getQuery)
                .sum();
        query = query > MAX_QUERY_FREE ? (query - MAX_QUERY_FREE) : 0;
        return new QueryDTO(query, currency.get(0));
    }
}

package com.accenture.chatgpt.service;

import com.accenture.chatgpt.model.BillingAddResponse;
import com.accenture.chatgpt.model.BillingResponse;
import com.accenture.chatgpt.model.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class BillingService extends BaseService {

    private HashMap<String, Integer> listBilling = new HashMap<>();
    @Value("${billing.query.value}")
    private BigDecimal billingQueryValue;
    private static final Integer MAX_QUERY_FREE=3;

    /**
     * Add query count
     *
     * @param user
     * @return
     */
    public Mono<BillingAddResponse> add(String user) {
        return Mono.just(user).doOnNext(x -> {
            Integer query = listBilling.getOrDefault(x, 0);
            query++;
            listBilling.put(x, query);
        }).map(y->new BillingAddResponse(y));
    }

    /**
     * Returns billing
     *
     * @param user
     * @return
     */
    public Mono<BillingResponse> getBilling(String user) {
        return Mono.just(user).map(x -> {
            Integer query = listBilling.getOrDefault(x, 0);
            query = query > MAX_QUERY_FREE ? (query-MAX_QUERY_FREE) : 0;
            return new BillingResponse(this.billingQueryValue.multiply(new BigDecimal(query)));
        });
    }

    /**
     * Set value billing
     * @param value
     * @return
     */
    public Mono<BigDecimal> setValueBilling(BigDecimal value){
        return Mono.just(value).doOnNext(x->{
            this.billingQueryValue=x;
        });
    }


}

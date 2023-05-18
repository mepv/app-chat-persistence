package com.accenture.chatgpt.service;

import com.accenture.chatgpt.client.BillingFeignClient;
import com.accenture.chatgpt.model.BillingAddRequest;
import com.accenture.chatgpt.model.Data;
import com.accenture.chatgpt.model.MessageRequest;
import com.accenture.chatgpt.model.MessageResponse;
import com.accenture.chatgpt.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class DataService extends BaseService{

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    BillingFeignClient billingFeignClient;

    private List<Data> listData = new ArrayList<>();

    public Mono<Data> addData(Data data) {
        return Mono.just(data).doOnNext(x -> {
            this.listData.add(x);
        });
    }

    public Flux<Data> getAllData() {
        return Flux.fromIterable(this.listData);
    }

    public Mono<MessageResponse> getAnswer(MessageRequest messageRequest, String token) {
        String username=this.getNameUserFromToken(token);
        this.billingFeignClient.addBilling(new BillingAddRequest(username));
        return Flux.fromIterable(this.listData)
                .filter(y -> y.getQuestion().toLowerCase(Locale.ROOT).contains(messageRequest.getQuestion().toLowerCase()))
                .switchIfEmpty(Flux.defer(()->Flux.just(new Data("", "Nose"))))
                .elementAt(0).map(x -> new MessageResponse(StringUtil.replaceText(x.getAnswer(), username)));
    }
}

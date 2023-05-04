package com.accenture.chatgpt.controller;


import java.time.Duration;

import com.accenture.chatgpt.model.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class HomeController {

    @GetMapping("/message")
    public Flux<Message> getAll() {
        Flux<Message> lista = Flux.just(new Message("Hola")
                , new Message("Hola 2")).delaySequence(Duration.ofSeconds(3));
        return lista;
    }
}
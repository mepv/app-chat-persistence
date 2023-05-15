package com.accenture.chatgpt.controller;


import java.time.Duration;

import com.accenture.chatgpt.model.Message;
import com.accenture.chatgpt.model.Subscriber;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("chatgpt")
public class HomeController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/message")
    public Flux<Message> getAll() {
        Flux<Message> lista = Flux.just(new Message("Hola")
                , new Message("Hola 2")).delaySequence(Duration.ofSeconds(3));
        return lista;
    }

    @GetMapping(path = "/numeros", produces = "text/event-stream")
    public Flux<Integer> all() {
        Flux<Integer> flux = Flux.range(1, 30)
                .delayElements(Duration.ofSeconds(1));

        flux.subscribe(System.out::println); // suscriptor 1
        flux.subscribe(Subscriber::multiplicar); // suscriptor 2
        return flux; // retornamos el elemento. Sería como el suscriptor 3
    }
}
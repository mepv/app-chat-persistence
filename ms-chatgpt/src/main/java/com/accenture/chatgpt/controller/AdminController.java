package com.accenture.chatgpt.controller;


import com.accenture.chatgpt.model.BillingResponse;
import com.accenture.chatgpt.model.Data;
import com.accenture.chatgpt.model.Response;
import com.accenture.chatgpt.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("chatgpt/admin")
public class AdminController {

    @Autowired
    DataService dataService;

    @PostMapping("/chat")
    public Mono<Response> postChat(@RequestBody Data data){
        return this.dataService.addData(data)
                .map(x -> new Response(HttpStatus.OK, "Ok", x));
    }

    @GetMapping("/chat")
    public Flux<Response> getChat() {
        return this.dataService.getAllData()
                .map(x -> new Response(HttpStatus.OK, "Ok", x));
    }

}
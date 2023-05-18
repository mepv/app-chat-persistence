package com.accenture.chatgpt.controller;


import com.accenture.chatgpt.model.MessageRequest;
import com.accenture.chatgpt.model.MessageResponse;
import com.accenture.chatgpt.model.Response;
import com.accenture.chatgpt.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("chatgpt/chat")
public class ChatController {

    @Autowired
    DataService dataService;

    @PostMapping("")
    public Mono<Response> postChat(@RequestBody MessageRequest messageRequest, @RequestHeader("Authorization") String authorization){
        return this.dataService.getAnswer(messageRequest, authorization)
                .map(x -> new Response(HttpStatus.OK, "Ok", x));
    }



}
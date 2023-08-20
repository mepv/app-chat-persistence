package com.accenture.chatgpt.controller;

import com.accenture.chatgpt.dto.MessageRequestDTO;
import com.accenture.chatgpt.dto.ResponseDTO;
import com.accenture.chatgpt.service.DataService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("chatgpt/chat")
public class ChatController {

    private final DataService dataService;

    public ChatController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    public Mono<ResponseDTO> postChat(@RequestBody MessageRequestDTO messageRequestDTO,
                                      @RequestHeader("Authorization") String authorization) {
        return this.dataService.getAnswer(messageRequestDTO, authorization)
                .map(response -> new ResponseDTO(HttpStatus.OK, "Ok", response));
    }
}

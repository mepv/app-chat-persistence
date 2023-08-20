package com.accenture.chatgpt.controller;

import com.accenture.chatgpt.dto.DataDTO;
import com.accenture.chatgpt.dto.UserDataDTO;
import com.accenture.chatgpt.dto.MessageRequestDTO;
import com.accenture.chatgpt.dto.ResponseDTO;
import com.accenture.chatgpt.service.DataService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("chatgpt/admin")
public class AdminController {

    private final DataService dataService;

    public AdminController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/chat")
    public Mono<ResponseDTO> postChat(@RequestBody DataDTO data,
                                      @RequestHeader("Authorization") String authorization) {
        return this.dataService.addData(data, authorization)
                .map(d -> new ResponseDTO(HttpStatus.OK, "Ok", d));
    }

    @GetMapping("/chat")
    public Mono<ResponseDTO> getChat() {
        return this.dataService.getAllData()
                .collectList()
                .map(data -> new ResponseDTO(HttpStatus.OK, "Ok", data));
    }

    @GetMapping("/data")
    public Mono<ResponseDTO> getDataTimeAsked(@RequestBody MessageRequestDTO messageRequestDTO) {
        return dataService.getDataTimeAsked(messageRequestDTO)
                .map(value -> new ResponseDTO(HttpStatus.OK,
                        String.format("La pregunta '%s' ha sido preguntada %s veces", messageRequestDTO.getQuestion(), value),
                        value));
    }

    @GetMapping("/data-asked")
    public List<UserDataDTO> getAdminsAskedOwnQuestions() {
        return dataService.getAdminsAskedOwnQuestions();
    }

    @GetMapping("/data-not-asked")
    public Mono<ResponseDTO> getDataNotAsked() {
        return dataService.getDataNotAsked()
                .map(list -> new ResponseDTO(HttpStatus.OK, "Preguntas registradas no consultadas", list));
    }

    @GetMapping("/data-by-dates")
    public Map<String, String> getQuestionsByDate() {
        return dataService.getQuestionsByDates();
    }
}

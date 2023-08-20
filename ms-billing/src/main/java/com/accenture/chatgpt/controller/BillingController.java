package com.accenture.chatgpt.controller;

import com.accenture.chatgpt.dto.UserDataDTO;
import com.accenture.chatgpt.dto.BillingAddRequest;
import com.accenture.chatgpt.dto.BillingValueRequestDTO;
import com.accenture.chatgpt.dto.Response;
import com.accenture.chatgpt.service.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    /**
     * Get billing from user
     *
     * @param user
     * @return
     */

    @GetMapping
    public Mono<Response> getBilling(@RequestParam("user") String user) {
        return this.billingService.getBilling(user)
                .map(response -> new Response(HttpStatus.OK, "Ok", response));
    }

    /**
     * Add query count billing by user
     *
     * @param billingAddRequest
     * @return
     */
    @PostMapping("/add")
    public Mono<Response> addBilling(@RequestBody BillingAddRequest billingAddRequest) {
        return this.billingService.add(billingAddRequest.getUser(), billingAddRequest.getQuestionUUID())
                .map(response -> new Response(HttpStatus.OK, "Insert query count successfully", response));
    }

    /**
     * Put value billing
     * @param billingValueRequestDTO
     * @return
     */
    @PutMapping
    public Mono<Response> putValueBilling(@RequestBody BillingValueRequestDTO billingValueRequestDTO) {
        return this.billingService.setValueBilling(billingValueRequestDTO.getValue())
                .map(value -> new Response(HttpStatus.OK, "Value billing update successfully", value));
    }

    @GetMapping("/users-without-debt")
    public Mono<List<String>> getUsersWithoutDebt() {
        return billingService.getUsersWithoutDebt();
    }

    @GetMapping("/admin/data-asked")
    public List<UserDataDTO> getAdminsAskedOwnQuestions() {
        return billingService.adminsAskedOwnQuestions();
    }

    @GetMapping("/admin/questions-by-date")
    public List<UserDataDTO> getQuestionsByDate(@RequestParam(name = "startDate") LocalDateTime startDate,
                                                @RequestParam(name = "endDate") LocalDateTime endDate) {
        return billingService.getQuestionsByDates(startDate, endDate);
    }
}

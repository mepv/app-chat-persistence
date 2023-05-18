package com.accenture.chatgpt.controller;


import com.accenture.chatgpt.model.*;
import com.accenture.chatgpt.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("billing")
public class BillingController {

    @Autowired
    BillingService billingService;

    /**
     * Get billig from user
     *
     * @param user
     * @return
     */

    @GetMapping("")
    public Mono<Response> getBilling(@RequestParam("user") String user) {
        return this.billingService.getBilling(user)
                .map(x -> new Response(HttpStatus.OK, "Ok", x));
    }

    /**
     * Add query count billling  by user
     *
     * @param billingAddRequest
     * @return
     */
    @PostMapping("/add")
    public Mono<Response> addBilling(@RequestBody BillingAddRequest billingAddRequest) {
        return this.billingService.add(billingAddRequest.getUser())
                .map(x -> new Response(HttpStatus.OK, "Insert query count successfully", x));
    }

    /**
     * Put value billing
     * @param billingValueRequest
     * @return
     */
    @PutMapping("")
    public Mono<Response> putValueBilling(@RequestBody BillingValueRequest billingValueRequest) {
        return this.billingService.setValueBilling(billingValueRequest.getValue())
                .map(x -> new Response(HttpStatus.OK, "Value billing update successfully", x));
    }
}
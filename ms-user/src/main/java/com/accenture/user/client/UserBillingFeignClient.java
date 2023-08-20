package com.accenture.user.client;

import com.accenture.user.dto.UserDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "ms-billing")
public interface UserBillingFeignClient {

    @GetMapping("/billing/users-without-debt")
    List<String> getUsersWithoutDebt();

    @GetMapping("/billing/admin/data-asked")
    List<UserDataDTO> getAdminsAskedOwnQuestions();

    @GetMapping("/billing/admin/questions-by-date")
    List<UserDataDTO> getQuestionsByDate(@RequestParam(name = "startDate") LocalDateTime startDate,
                                         @RequestParam(name = "endDate") LocalDateTime endDate);
}

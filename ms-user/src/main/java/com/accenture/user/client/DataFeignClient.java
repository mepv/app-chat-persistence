package com.accenture.user.client;

import com.accenture.user.dto.UserDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-chatgpt")
public interface DataFeignClient {

    @GetMapping("chatgpt/admin/data-asked")
    List<UserDataDTO> getAdminsAskedOwnQuestions();

    @GetMapping("/chatgpt/admin/data-by-dates")
    Map<String, String> getQuestionsByDate();
}

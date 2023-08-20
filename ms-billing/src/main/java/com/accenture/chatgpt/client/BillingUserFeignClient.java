package com.accenture.chatgpt.client;

import com.accenture.chatgpt.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-user")
public interface BillingUserFeignClient {

    @GetMapping("/user/search/username")
    UserDTO getUsername(@RequestParam("username") String username);
}

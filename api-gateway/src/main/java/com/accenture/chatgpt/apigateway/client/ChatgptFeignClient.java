package com.accenture.chatgpt.apigateway.client;

import com.accenture.chatgpt.apigateway.model.MessageRequest;
import com.accenture.chatgpt.apigateway.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "api-gateway")
public interface ChatgptFeignClient {
    @PostMapping("chatgpt/chat")
    Response postChat(@RequestBody MessageRequest messageRequest);
}

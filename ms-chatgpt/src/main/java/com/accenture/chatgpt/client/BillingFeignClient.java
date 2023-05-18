package com.accenture.chatgpt.client;

import com.accenture.chatgpt.model.BillingAddRequest;
import com.accenture.chatgpt.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name="ms-billing")
public interface BillingFeignClient {

	@PostMapping("/billing/add")
	public Response addBilling(@RequestBody BillingAddRequest billingAddRequest);
}

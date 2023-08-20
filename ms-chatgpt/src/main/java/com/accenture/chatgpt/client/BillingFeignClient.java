package com.accenture.chatgpt.client;

import com.accenture.chatgpt.dto.BillingAddRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="ms-billing")
public interface BillingFeignClient {

	@PostMapping("/billing/add")
	void addBilling(@RequestBody BillingAddRequestDTO billingAddRequest);
}

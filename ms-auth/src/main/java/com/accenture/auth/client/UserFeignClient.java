package com.accenture.auth.client;

import com.accenture.auth.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="ms-usuario")
public interface UserFeignClient {

	@GetMapping("/user/search/username")
	public User getByUsername(@RequestParam String username);
}

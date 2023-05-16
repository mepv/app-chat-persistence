package com.accenture.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@EnableFeignClients(basePackages = {"com.accenture.auth.client"})
//@EnableEurekaClient
@SpringBootApplication
public class Application  implements CommandLineRunner {
//	@Autowired
//	private BCryptPasswordEncoder passwordEncode;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
//		String password = "654321";
//
//		for (int i = 0; i < 4; i++) {
//			String passwordBCrypt = passwordEncode.encode(password);
//			System.out.println("Password=>" + passwordBCrypt);
//		}

	}
}

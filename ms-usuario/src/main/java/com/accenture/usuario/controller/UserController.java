package com.accenture.usuario.controller;


import java.time.Duration;

import com.accenture.usuario.model.User;
import com.accenture.usuario.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }
    @GetMapping("/search/username")
    public ResponseEntity<User> getByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(this.userService.getUserByUsername(username));
    }

}
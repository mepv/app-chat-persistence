package com.accenture.user.controller;


import com.accenture.user.model.User;
import com.accenture.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
package com.accenture.user.controller;

import com.accenture.user.dto.AdminUserDTO;
import com.accenture.user.dto.RegistrationUserDTO;
import com.accenture.user.dto.Response;
import com.accenture.user.dto.UserDTO;
import com.accenture.user.dto.UserDataDateDTO;
import com.accenture.user.model.User;
import com.accenture.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search/username")
    public ResponseEntity<User> getByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/search/usernames")
    public List<UserDTO> findAllByRole(@RequestParam(value = "role") String role) {
        return userService.usersByRole(role);
    }

    @PostMapping("/username")
    @ResponseStatus(HttpStatus.CREATED)
    public Response saveUser(@RequestBody RegistrationUserDTO userDTO) {
        return new Response(HttpStatus.CREATED, "created", userService.saveUserByUsername(userDTO));
    }

    @PostMapping("/admin/username")
    @ResponseStatus(HttpStatus.CREATED)
    public Response saveUserAdmin(@RequestBody RegistrationUserDTO userDTO) {
        return new Response(HttpStatus.CREATED, "created", userService.saveUserByUsername(userDTO));
    }

    @GetMapping("admin/users-without-debt")
    public List<UserDTO> getUsersWithoutDebt() {
        return userService.usersWithoutDebt();
    }

    @GetMapping("/admin/asked-own-questions")
    public List<AdminUserDTO> findAdmins() {
        return userService.adminsAskedOwnQuestions();
    }

    @GetMapping("/admin/users-by-date")
    public List<UserDataDateDTO> getUsersByDate(@RequestParam(name = "startDate") String startDate,
                                                @RequestParam(name = "endDate") String endDate) {
        String zone = "T00:00:00";
        LocalDateTime start = LocalDateTime.parse(startDate.concat(zone));
        LocalDateTime end = LocalDateTime.parse(endDate.concat(zone));
        return userService.getUserThatAskedQuestionsByDates(start, end);
    }
}

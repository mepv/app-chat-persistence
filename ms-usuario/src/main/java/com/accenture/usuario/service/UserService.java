package com.accenture.usuario.service;


import com.accenture.usuario.model.Role;
import com.accenture.usuario.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    @PostConstruct
    private void loadsUser() {
        Role roleAdmin = new Role();
        roleAdmin.setId(1);
        roleAdmin.setName("ADMIN");
        Role roleUser = new Role();
        roleUser.setId(2);
        roleUser.setName("USER");
        User userAdmin = new User();
        userAdmin.setId(1);
        userAdmin.setUsername("admin");
        userAdmin.setPassword("$2a$10$fn5BB.BgRINLPzP/RiD2K.6FtWwLxXqks0qGwwE13VwOZkmhiEKRq");
        userAdmin.setRole(roleAdmin);
        User userUser = new User();
        userUser.setId(2);
        userUser.setUsername("user");
        userUser.setPassword("$2a$10$fn5BB.BgRINLPzP/RiD2K.6FtWwLxXqks0qGwwE13VwOZkmhiEKRq");
        userUser.setRole(roleUser);
        this.users.add(userAdmin);
        this.users.add(userUser);
    }

    public User getUserByUsername(String username) {
        List<User> resultUserList = this.users.stream()
                .filter(user -> user.getUsername().equals(username))
                .collect(Collectors.toList());
        if (Objects.isNull(resultUserList) || resultUserList.size() != 1) {
            return null;
        }
        return resultUserList.get(0);
    }
}

package com.accenture.auth.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.accenture.auth.client.UserFeignClient;
import com.accenture.auth.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserFeignClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.accenture.auth.model.User user = userClient.getByUsername(username);

        if (user == null) {
            log.error("Error en el login, no existe el usuario '" + username + "' en el sistema");
            throw new UsernameNotFoundException("Error en el login, no existe el usuario '" + username + "' en el sistema");
        }
        List<Role> roles = Collections.singletonList(user.getRole());
        List<GrantedAuthority> authorities = Collections.singletonList(user.getRole())
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .peek(authority -> log.info("Role: " + authority.getAuthority()))
                .collect(Collectors.toList());
        log.info("Usuario autenticado: " + username);
        return new User(user.getUsername(), user.getPassword(), true, true,
                true, true, authorities);
    }


}

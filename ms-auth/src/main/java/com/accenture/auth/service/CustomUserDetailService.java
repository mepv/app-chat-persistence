///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.accenture.auth.service;
//
//
//import com.accenture.auth.client.UserFeignClient;
//import com.accenture.auth.model.CustomUser;
//import com.accenture.auth.model.Role;
//import com.accenture.auth.model.User;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * @author rafa22
// */
//@Service
//public class CustomUserDetailService implements UserDetailsService {
//
//    private Logger logger = LoggerFactory.getLogger(UserService.class);
//    @Autowired
//    private UserFeignClient userClient;
//
//
//    public CustomUserDetailService() {
//    }
//
//    /**
//     * Load by user name method, it calls to ms user
//     * @param username
//     * @return
//     * @throws UsernameNotFoundException
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = this.userClient.getByUsername(username);
//        if (Objects.isNull(user)) {
//            logger.info(String.format("User logged wrong: %s", username));
//            throw new UsernameNotFoundException(String.format("User %s not found.", username));
//        }
//        List<Role> roles = Collections.singletonList(user.getRole());
//        if (Objects.isNull(roles)) {
//            logger.error(String.format("User without roles: %s", username));
//            throw new RuntimeException("User without roles");
//        }
//        Collection<GrantedAuthority> grantedAuthorities = roles.stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList());
//        logger.info(String.format("User logged: %s", username));
//        return new CustomUser(user.getUsername(),
//                user.getPassword(),
//                grantedAuthorities,
//                user.getId()
//        );
//    }
//
//}

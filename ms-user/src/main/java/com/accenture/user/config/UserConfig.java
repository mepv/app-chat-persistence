package com.accenture.user.config;

import com.accenture.user.model.Gender;
import com.accenture.user.model.Role;
import com.accenture.user.model.User;
import com.accenture.user.repository.RoleRepository;
import com.accenture.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.accenture.user.model.Role.SCOPE_ROLE_ADMIN;
import static com.accenture.user.model.Role.SCOPE_ROLE_USER;

@Configuration
public class UserConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserConfig(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            List<Role> roles = new ArrayList<>();
            Role roleAdmin = new Role();
            roleAdmin.setId(1L);
            roleAdmin.setName(SCOPE_ROLE_ADMIN);

            Role roleUser = new Role();
            roleUser.setId(2L);
            roleUser.setName(SCOPE_ROLE_USER);

            roles.add(roleAdmin);
            roles.add(roleUser);
            roleRepository.saveAll(roles);

            User user = new User();
            user.setUsername("admin");
            user.setPassword("$2a$10$fn5BB.BgRINLPzP/RiD2K.6FtWwLxXqks0qGwwE13VwOZkmhiEKRq");
            user.setCreatedAt(LocalDateTime.now());
            user.setCountry("Argentina");
            user.setGender(Gender.MASCULINO);
            user.setFirstName("Java");
            user.setLastName("Shark");
            user.setPhoneNumber("555-5555");
            user.setRole(roleAdmin);

            Optional<User> userOptional = userRepository.findByUsername("admin");
            if (!userOptional.isPresent()) {
                userRepository.save(user);
            }
        };
    }
}

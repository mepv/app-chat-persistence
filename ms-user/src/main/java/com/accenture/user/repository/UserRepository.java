package com.accenture.user.repository;

import com.accenture.user.model.Role;
import com.accenture.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findAllByRole(Role role);

    List<User> findAllByUsernameIn(List<String> users);
}

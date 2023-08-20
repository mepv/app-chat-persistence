package com.accenture.chatgpt.repository;

import com.accenture.chatgpt.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DataRepository extends JpaRepository<Data, Long> {

    Optional<Data> findByQuestion(String question);

    Optional<Data> findByUuid(UUID uuid);
}

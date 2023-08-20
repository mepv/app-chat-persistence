package com.accenture.chatgpt.repository;

import com.accenture.chatgpt.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findByValue(String value);
}

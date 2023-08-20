package com.accenture.chatgpt.repository;

import com.accenture.chatgpt.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BillingRepository extends JpaRepository<Billing, Long> {

    List<Billing> findAllByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Billing b SET b.query = b.query + 1 WHERE b.questionUUID = :questionUUID AND b.username = :username")
    void incrementQueryByQuestionUUIDAndUsername(UUID questionUUID, String username);

    Optional<Billing> findByQuestionUUIDAndUsername(UUID questionUUID, String username);

    List<Billing> findByAskedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}

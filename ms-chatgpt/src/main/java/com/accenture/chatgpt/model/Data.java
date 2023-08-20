package com.accenture.chatgpt.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "questions")
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID uuid;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "user_name")
    private String username;
    @Column(length = 4000)
    private String question;
    @Column(name = "times_question_was_asked")
    private Integer timesQuestionWasAsked;
    @ManyToOne(fetch = FetchType.LAZY)
    private Answer answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Integer getTimesQuestionWasAsked() {
        return timesQuestionWasAsked;
    }

    public void setTimesQuestionWasAsked(Integer timesQuestionWasAsked) {
        this.timesQuestionWasAsked = timesQuestionWasAsked;
    }
}

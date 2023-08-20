package com.accenture.chatgpt.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class DataDTO {

    @JsonIgnore
    private Long id;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private Integer timesQuestionWasAsked;
    private String question;
    private AnswerDTO answer;

    public DataDTO() {
    }

    public DataDTO(String question, AnswerDTO answer) {
        this.question = question;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getTimesQuestionWasAsked() {
        return timesQuestionWasAsked;
    }

    public void setTimesQuestionWasAsked(Integer timesQuestionWasAsked) {
        this.timesQuestionWasAsked = timesQuestionWasAsked;
    }

    public AnswerDTO getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerDTO answer) {
        this.answer = answer;
    }
}

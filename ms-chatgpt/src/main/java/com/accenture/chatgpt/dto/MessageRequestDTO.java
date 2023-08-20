package com.accenture.chatgpt.dto;

public class MessageRequestDTO {

    private String question;

    public MessageRequestDTO() {
    }

    public MessageRequestDTO(String text) {
        this.question = text;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}

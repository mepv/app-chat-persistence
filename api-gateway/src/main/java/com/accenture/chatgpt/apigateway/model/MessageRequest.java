package com.accenture.chatgpt.apigateway.model;

public class MessageRequest {
    public MessageRequest() {
    }

    public MessageRequest(String text) {
        this.question = text;
    }

    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}

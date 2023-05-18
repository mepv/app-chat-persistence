package com.accenture.chatgpt.model;

public class MessageResponse {
    private String answer;

    public MessageResponse(String text) {
        this.answer = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

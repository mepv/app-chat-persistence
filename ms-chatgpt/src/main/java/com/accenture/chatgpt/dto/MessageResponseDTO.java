package com.accenture.chatgpt.dto;

public class MessageResponseDTO {

    private String answer;

    public MessageResponseDTO(String text) {
        this.answer = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

package com.accenture.chatgpt.dto;

import java.util.List;

public class UserDataDTO {

    private String user;
    private List<String> questions;

    public UserDataDTO(String user, List<String> questions) {
        this.user = user;
        this.questions = questions;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }
}

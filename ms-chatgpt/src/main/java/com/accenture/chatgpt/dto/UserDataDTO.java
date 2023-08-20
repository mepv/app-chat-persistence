package com.accenture.chatgpt.dto;

import java.util.List;
import java.util.Map;

public class UserDataDTO {

    private String user;
    private List<String> questions;
    private Map<String, String> questionsMap;

    public UserDataDTO(String user, List<String> questions, Map<String, String> questionsMap) {
        this.user = user;
        this.questions = questions;
        this.questionsMap = questionsMap;
    }

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

    public Map<String, String> getQuestionsMap() {
        return questionsMap;
    }

    public void setQuestionsMap(Map<String, String> questionsMap) {
        this.questionsMap = questionsMap;
    }
}

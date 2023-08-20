package com.accenture.user.dto;

import java.util.List;
import java.util.Map;

public class UserDataDTO {

    private String user;
    private List<String> questions;
    private Map<String, String> questionsMap;

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

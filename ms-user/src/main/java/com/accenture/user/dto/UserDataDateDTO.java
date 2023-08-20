package com.accenture.user.dto;

import java.util.List;

public class UserDataDateDTO {

    private String username;
    private List<QuestionDateDTO> data;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<QuestionDateDTO> getData() {
        return data;
    }

    public void setData(List<QuestionDateDTO> data) {
        this.data = data;
    }
}

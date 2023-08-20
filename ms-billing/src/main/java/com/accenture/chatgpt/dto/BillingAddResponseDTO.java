package com.accenture.chatgpt.dto;


public class BillingAddResponseDTO {

    private String user;

    public BillingAddResponseDTO(String user) {
        this.user = user;
    }

    public BillingAddResponseDTO() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

package com.accenture.chatgpt.model;


public class BillingAddResponse {
   private String user;

    public BillingAddResponse(String user) {
        this.user = user;
    }

    public BillingAddResponse() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

package com.accenture.chatgpt.dto;

import java.util.UUID;

public class BillingAddRequest {

    private String user;
    private UUID questionUUID;

    public BillingAddRequest(String user, UUID questionUUID) {
        this.user = user;
        this.questionUUID = questionUUID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public UUID getQuestionUUID() {
        return questionUUID;
    }

    public void setQuestionUUID(UUID questionUUID) {
        this.questionUUID = questionUUID;
    }
}

package com.accenture.chatgpt.model;

public class Billing {
    private String user;
    private Integer query;

    public Billing() {
    }

    public Billing(String user, Integer query) {
        this.user = user;
        this.query = query;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getQuery() {
        return query;
    }

    public void setQuery(Integer query) {
        this.query = query;
    }
}

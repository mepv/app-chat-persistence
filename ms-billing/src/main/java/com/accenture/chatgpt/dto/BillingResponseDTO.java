package com.accenture.chatgpt.dto;

import java.math.BigDecimal;

public class BillingResponseDTO {

    private String firstName;
    private String lastName;
    private BigDecimal value;
    private String currency;

    public BillingResponseDTO() {
    }

    public BillingResponseDTO(String firstName, String lastName, BigDecimal value, String currency) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.value = value;
        this.currency = currency;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

package com.accenture.chatgpt.model;

import java.math.BigDecimal;

public class BillingResponse {
    private BigDecimal value;

    public BillingResponse() {
    }

    public BillingResponse(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}

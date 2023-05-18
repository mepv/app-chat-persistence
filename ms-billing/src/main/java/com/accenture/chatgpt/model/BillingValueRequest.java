package com.accenture.chatgpt.model;

import java.math.BigDecimal;

public class BillingValueRequest {
    public BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}

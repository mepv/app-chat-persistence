package com.accenture.chatgpt.dto;

import java.math.BigDecimal;

public class BillingValueRequestDTO {

    private BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}

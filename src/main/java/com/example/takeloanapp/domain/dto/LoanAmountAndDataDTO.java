package com.example.takeloanapp.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LoanAmountAndDataDTO {

    public BigDecimal bigDecimalAmount;
    public LocalDateTime localDateTime;

    public LoanAmountAndDataDTO(BigDecimal result, LocalDateTime lastRepayment) {
        this.bigDecimalAmount = result;
        this.localDateTime = lastRepayment;
    }
}

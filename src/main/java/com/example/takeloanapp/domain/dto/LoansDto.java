package com.example.takeloanapp.domain.dto;

import com.example.takeloanapp.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoansDto {

    private Long id;
    private String productName;
    private int periodInMonth;
    private LocalDate startDate;
    private LocalDate endDate;
    private int dayOfInstalmentRepayment;
    private BigDecimal loanAmount;
    private BigDecimal loanRate;
    private BigDecimal loanTotalInterest;
    private BigDecimal nextInstalmentInterestRepayment;
    private BigDecimal nextInstalmentCapitalRepayment;
    private boolean isActive;
    private LocalDate registrationDate;
    private boolean hasArrears;
    private int counterDaysArrears;
    private BigDecimal penaltyInterest;
    private BigDecimal penaltyInterestAmount;
    private Long customerId;
    private String loanAccountNumber;
    private boolean isClosed;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoansDto)) return false;

        LoansDto loansDto = (LoansDto) o;

        if (periodInMonth != loansDto.periodInMonth) return false;
        if (isActive != loansDto.isActive) return false;
        if (!id.equals(loansDto.id)) return false;
        if (productName != null ? !productName.equals(loansDto.productName) : loansDto.productName != null)
            return false;
        if (startDate != null ? !startDate.equals(loansDto.startDate) : loansDto.startDate != null) return false;
        if (endDate != null ? !endDate.equals(loansDto.endDate) : loansDto.endDate != null) return false;
        if (loanAmount != null ? !loanAmount.equals(loansDto.loanAmount) : loansDto.loanAmount != null) return false;
        return loanRate != null ? loanRate.equals(loansDto.loanRate) : loansDto.loanRate == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + periodInMonth;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (loanAmount != null ? loanAmount.hashCode() : 0);
        result = 31 * result + (loanRate != null ? loanRate.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }
}

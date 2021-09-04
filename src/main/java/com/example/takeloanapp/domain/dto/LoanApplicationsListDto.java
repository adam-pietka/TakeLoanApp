package com.example.takeloanapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoanApplicationsListDto {

    private Long id;
    private Long customerId;
    private Long loansId;
    private String employmentForm;
    private BigDecimal incomeAmount;
    private String employerName;
    private String employerNipNumber;
    private String employerAddress;
    private String employerPhoneNumber;
    private BigDecimal otherLiabilities;
    private BigDecimal loanAmount;
    private int repaymentPeriodInMonth;
    private boolean isApplicationAccepted;
    private LocalDate dateOfRegistrationOfApplication;
    private LocalDate dataOfClosedOfApplication;
    private String accountNumberForPaymentOfLoan;
    private boolean isPayouts;
    private LocalDate dateOfPayout;
    private boolean isClosed;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanApplicationsListDto)) return false;

        LoanApplicationsListDto that = (LoanApplicationsListDto) o;

        if (repaymentPeriodInMonth != that.repaymentPeriodInMonth) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (employmentForm != null ? !employmentForm.equals(that.employmentForm) : that.employmentForm != null)
            return false;
        if (incomeAmount != null ? !incomeAmount.equals(that.incomeAmount) : that.incomeAmount != null) return false;
        if (employerName != null ? !employerName.equals(that.employerName) : that.employerName != null) return false;
        if (employerNipNumber != null ? !employerNipNumber.equals(that.employerNipNumber) : that.employerNipNumber != null)
            return false;
        if (employerAddress != null ? !employerAddress.equals(that.employerAddress) : that.employerAddress != null)
            return false;
        if (employerPhoneNumber != null ? !employerPhoneNumber.equals(that.employerPhoneNumber) : that.employerPhoneNumber != null)
            return false;
        if (otherLiabilities != null ? !otherLiabilities.equals(that.otherLiabilities) : that.otherLiabilities != null)
            return false;
        return loanAmount != null ? loanAmount.equals(that.loanAmount) : that.loanAmount == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (employmentForm != null ? employmentForm.hashCode() : 0);
        result = 31 * result + (incomeAmount != null ? incomeAmount.hashCode() : 0);
        result = 31 * result + (employerName != null ? employerName.hashCode() : 0);
        result = 31 * result + (employerNipNumber != null ? employerNipNumber.hashCode() : 0);
        result = 31 * result + (employerAddress != null ? employerAddress.hashCode() : 0);
        result = 31 * result + (employerPhoneNumber != null ? employerPhoneNumber.hashCode() : 0);
        result = 31 * result + (otherLiabilities != null ? otherLiabilities.hashCode() : 0);
        result = 31 * result + (loanAmount != null ? loanAmount.hashCode() : 0);
        result = 31 * result + repaymentPeriodInMonth;
        return result;
    }
}

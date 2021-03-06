package com.example.takeloanapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanCashFlowDto {

    private Long transactionId ;
    private Long loansId;
    private BigDecimal repaymentAmount;
    private BigDecimal postingsAsInstalment;
    private BigDecimal postingsAsArrears;
    private boolean isAnInstallment;
    private boolean isDisbursement;
    private String accountNumber;
    private LocalDateTime transactionTimeStamp;
    private LocalDateTime postingsTimeStamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanCashFlowDto)) return false;

        LoanCashFlowDto that = (LoanCashFlowDto) o;

        if (isAnInstallment != that.isAnInstallment) return false;
        if (isDisbursement != that.isDisbursement) return false;
        if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null)
            return false;
        if (loansId != null ? !loansId.equals(that.loansId) : that.loansId != null) return false;
        if (repaymentAmount != null ? !repaymentAmount.equals(that.repaymentAmount) : that.repaymentAmount != null)
            return false;
        if (postingsAsInstalment != null ? !postingsAsInstalment.equals(that.postingsAsInstalment) : that.postingsAsInstalment != null)
            return false;
        if (postingsAsArrears != null ? !postingsAsArrears.equals(that.postingsAsArrears) : that.postingsAsArrears != null)
            return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        return transactionTimeStamp != null ? transactionTimeStamp.equals(that.transactionTimeStamp) : that.transactionTimeStamp == null;
    }

    @Override
    public int hashCode() {
        int result = transactionId != null ? transactionId.hashCode() : 0;
        result = 31 * result + (loansId != null ? loansId.hashCode() : 0);
        result = 31 * result + (repaymentAmount != null ? repaymentAmount.hashCode() : 0);
        result = 31 * result + (postingsAsInstalment != null ? postingsAsInstalment.hashCode() : 0);
        result = 31 * result + (postingsAsArrears != null ? postingsAsArrears.hashCode() : 0);
        result = 31 * result + (isAnInstallment ? 1 : 0);
        result = 31 * result + (isDisbursement ? 1 : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (transactionTimeStamp != null ? transactionTimeStamp.hashCode() : 0);
        return result;
    }
}

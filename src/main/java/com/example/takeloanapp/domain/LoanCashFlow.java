package com.example.takeloanapp.domain;

import com.sun.istack.NotNull;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOAN_CASH_FLOW")
public class LoanCashFlow {

    private Long transactionId ;
    private Loans loans;
    private BigDecimal repaymentAmount;
    private BigDecimal postingsAsInstalment;
    private BigDecimal postingsAsArrears;
    private boolean isAnInstallment;
    private boolean isDisbursement;
    private String accountNumber;
    private LocalDateTime transactionTimeStamp;
    private LocalDateTime postingsTimeStamp;

    public LoanCashFlow() {
    }

    public LoanCashFlow(Long transactionId, Loans loans, BigDecimal repaymentAmount, BigDecimal postingsAsInstalment, BigDecimal postingsAsArrears, boolean isAnInstallment, boolean isDisbursement, String accountNumber, LocalDateTime transactionTimeStamp, LocalDateTime postingsTimeStamp) {
        this.transactionId = transactionId;
        this.loans = loans;
        this.repaymentAmount = repaymentAmount;
        this.postingsAsInstalment = postingsAsInstalment;
        this.postingsAsArrears = postingsAsArrears;
        this.isAnInstallment = isAnInstallment;
        this.isDisbursement = isDisbursement;
        this.accountNumber = accountNumber;
        this.transactionTimeStamp = transactionTimeStamp;
        this.postingsTimeStamp = postingsTimeStamp;
    }

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "TRANSACTION_ID", unique = true)
    public Long getTransactionId() {
        return transactionId;
    }

    @ManyToOne
    @JoinColumn(name = "LOAN_ID")
    public Loans getLoans() {
        return loans;
    }

    public BigDecimal getRepaymentAmount() {
        return repaymentAmount;
    }

    public BigDecimal getPostingsAsInstalment() {
        return postingsAsInstalment;
    }

    public BigDecimal getPostingsAsArrears() {
        return postingsAsArrears;
    }

    public boolean isAnInstallment() {
        return isAnInstallment;
    }

    public boolean isDisbursement() {
        return isDisbursement;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public LocalDateTime getTransactionTimeStamp() {
        return transactionTimeStamp;
    }

    public LocalDateTime getPostingsTimeStamp() {
        return postingsTimeStamp;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setLoans(Loans loans) {
        this.loans = loans;
    }

    public void setRepaymentAmount(BigDecimal repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public void setPostingsAsInstalment(BigDecimal postingsAsInstalment) {
        this.postingsAsInstalment = postingsAsInstalment;
    }

    public void setPostingsAsArrears(BigDecimal postingsAsArrears) {
        this.postingsAsArrears = postingsAsArrears;
    }

    public void setAnInstallment(boolean anInstallment) {
        isAnInstallment = anInstallment;
    }

    public void setDisbursement(boolean disbursement) {
        isDisbursement = disbursement;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setTransactionTimeStamp(LocalDateTime transactionTimeStamp) {
        this.transactionTimeStamp = transactionTimeStamp;
    }

    public void setPostingsTimeStamp(LocalDateTime postingsTimeStamp) {
        this.postingsTimeStamp = postingsTimeStamp;
    }
}

package com.example.takeloanapp.domain;

import com.sun.istack.NotNull;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LOANS")
public class Loans {

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
    private BigDecimal payedInterests;
    private BigDecimal payedCapital;
    private boolean isActive;
    private LocalDate registrationDate;
    private boolean hasArrears;
    private int counterDaysArrears;
    private BigDecimal penaltyInterest;
    private BigDecimal penaltyInterestAmount;
    private Customer customer;
    private String loanAccountNumber;
    private boolean isClosed;
    private List<LoanCashFlow> loanCashFlows = new ArrayList<>();

    public Loans() {
    }

    public Loans(int periodInMonth, int dayOfInstalmentRepayment, BigDecimal loanAmount) {
        this.periodInMonth = periodInMonth;
        this.dayOfInstalmentRepayment = dayOfInstalmentRepayment;
        this.loanAmount = loanAmount;
    }

    public Loans(Long id, String productName, int periodInMonth, LocalDate startDate, LocalDate endDate, int dayOfInstalmentRepayment, BigDecimal loanAmount, BigDecimal loanRate, BigDecimal loanTotalInterest, BigDecimal nextInstalmentInterestRepayment, BigDecimal nextInstalmentCapitalRepayment, BigDecimal payedInterests, BigDecimal payedCapital, boolean isActive, LocalDate registrationDate, boolean hasArrears, int counterDaysArrears, BigDecimal penaltyInterest, BigDecimal penaltyInterestAmount, Customer customer, String loanAccountNumber, boolean isClosed, List<LoanCashFlow> loanCashFlows) {
        this.id = id;
        this.productName = productName;
        this.periodInMonth = periodInMonth;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dayOfInstalmentRepayment = dayOfInstalmentRepayment;
        this.loanAmount = loanAmount;
        this.loanRate = loanRate;
        this.loanTotalInterest = loanTotalInterest;
        this.nextInstalmentInterestRepayment = nextInstalmentInterestRepayment;
        this.nextInstalmentCapitalRepayment = nextInstalmentCapitalRepayment;
        this.payedInterests = payedInterests;
        this.payedCapital = payedCapital;
        this.isActive = isActive;
        this.registrationDate = registrationDate;
        this.hasArrears = hasArrears;
        this.counterDaysArrears = counterDaysArrears;
        this.penaltyInterest = penaltyInterest;
        this.penaltyInterestAmount = penaltyInterestAmount;
        this.customer = customer;
        this.loanAccountNumber = loanAccountNumber;
        this.isClosed = isClosed;
        this.loanCashFlows = loanCashFlows;
    }

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "LOAN_ID", unique = true)
    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getPeriodInMonth() {
        return periodInMonth;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getDayOfInstalmentRepayment() {
        return dayOfInstalmentRepayment;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public BigDecimal getLoanTotalInterest() {
        return loanTotalInterest;
    }

    public BigDecimal getNextInstalmentInterestRepayment() {
        return nextInstalmentInterestRepayment;
    }

    public BigDecimal getNextInstalmentCapitalRepayment() {
        return nextInstalmentCapitalRepayment;
    }

    public BigDecimal getPayedInterests() {
        return payedInterests;
    }

    public BigDecimal getPayedCapital() {
        return payedCapital;
    }

    public boolean isActive() {
        return isActive;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public boolean isHasArrears() {
        return hasArrears;
    }

    public int getCounterDaysArrears() {
        return counterDaysArrears;
    }

    public BigDecimal getPenaltyInterest() {
        return penaltyInterest;
    }

    public BigDecimal getPenaltyInterestAmount() {
        return penaltyInterestAmount;
    }

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    public Customer getCustomer() {
        return customer;
    }

    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public boolean isClosed() {
        return isClosed;
    }

    @OneToMany(
            targetEntity = LoanCashFlow.class,
            mappedBy = "loans",
//            fetch = FetchType.LAZY
            fetch = FetchType.EAGER
    )
    public List<LoanCashFlow> getLoanCashFlows() {
        return loanCashFlows;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPeriodInMonth(int periodInMonth) {
        this.periodInMonth = periodInMonth;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setDayOfInstalmentRepayment(int dayOfInstalmentRepayment) {
        this.dayOfInstalmentRepayment = dayOfInstalmentRepayment;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public void setLoanTotalInterest(BigDecimal loanTotalInterest) {
        this.loanTotalInterest = loanTotalInterest;
    }

    public void setNextInstalmentInterestRepayment(BigDecimal nextInstalmentInterestRepayment) {
        this.nextInstalmentInterestRepayment = nextInstalmentInterestRepayment;
    }

    public void setNextInstalmentCapitalRepayment(BigDecimal nextInstalmentCapitalRepayment) {
        this.nextInstalmentCapitalRepayment = nextInstalmentCapitalRepayment;
    }

    public void setPayedInterests(BigDecimal payedInterests) {
        this.payedInterests = payedInterests;
    }

    public void setPayedCapital(BigDecimal payedCapital) {
        this.payedCapital = payedCapital;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setHasArrears(boolean hasArrears) {
        this.hasArrears = hasArrears;
    }

    public void setCounterDaysArrears(int counterDaysArrears) {
        this.counterDaysArrears = counterDaysArrears;
    }

    public void setPenaltyInterest(BigDecimal penaltyInterest) {
        this.penaltyInterest = penaltyInterest;
    }

    public void setPenaltyInterestAmount(BigDecimal penaltyInterestAmount) {
        this.penaltyInterestAmount = penaltyInterestAmount;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public void setLoanCashFlows(List<LoanCashFlow> loanCashFlows) {
        this.loanCashFlows = loanCashFlows;
    }
}
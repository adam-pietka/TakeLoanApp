package com.example.takeloanapp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "LOAN_APPLICATIONS_LIST")
public class LoanApplicationsList {

    private Long id;
    private Customer customer;
    private Loans loans;
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

    public LoanApplicationsList() {
    }

    public LoanApplicationsList(Long id, Customer customer, Loans loans, String employmentForm, BigDecimal incomeAmount, String employerName, String employerNipNumber, String employerAddress, String employerPhoneNumber, BigDecimal otherLiabilities, BigDecimal loanAmount, int repaymentPeriodInMonth, boolean isApplicationAccepted, LocalDate dateOfRegistrationOfApplication, LocalDate dataOfClosedOfApplication, String accountNumberForPaymentOfLoan, boolean isPayouts, LocalDate dateOfPayout, boolean isClosed) {
        this.id = id;
        this.customer = customer;
        this.loans = loans;
        this.employmentForm = employmentForm;
        this.incomeAmount = incomeAmount;
        this.employerName = employerName;
        this.employerNipNumber = employerNipNumber;
        this.employerAddress = employerAddress;
        this.employerPhoneNumber = employerPhoneNumber;
        this.otherLiabilities = otherLiabilities;
        this.loanAmount = loanAmount;
        this.repaymentPeriodInMonth = repaymentPeriodInMonth;
        this.isApplicationAccepted = isApplicationAccepted;
        this.dateOfRegistrationOfApplication = dateOfRegistrationOfApplication;
        this.dataOfClosedOfApplication = dataOfClosedOfApplication;
        this.accountNumberForPaymentOfLoan = accountNumberForPaymentOfLoan;
        this.isPayouts = isPayouts;
        this.dateOfPayout = dateOfPayout;
        this.isClosed = isClosed;
    }

    public LoanApplicationsList(Customer customer, Loans loans, String employmentForm, BigDecimal incomeAmount, String employerName, String employerNipNumber, String employerAddress, String employerPhoneNumber, BigDecimal otherLiabilities, BigDecimal loanAmount, int repaymentPeriodInMonth, boolean isApplicationAccepted, LocalDate dateOfRegistrationOfApplication, LocalDate dataOfClosedOfApplication, String accountNumberForPaymentOfLoan, boolean isPayouts, LocalDate dateOfPayout, boolean isClosed) {
        this.customer = customer;
        this.loans = loans;
        this.employmentForm = employmentForm;
        this.incomeAmount = incomeAmount;
        this.employerName = employerName;
        this.employerNipNumber = employerNipNumber;
        this.employerAddress = employerAddress;
        this.employerPhoneNumber = employerPhoneNumber;
        this.otherLiabilities = otherLiabilities;
        this.loanAmount = loanAmount;
        this.repaymentPeriodInMonth = repaymentPeriodInMonth;
        this.isApplicationAccepted = isApplicationAccepted;
        this.dateOfRegistrationOfApplication = dateOfRegistrationOfApplication;
        this.dataOfClosedOfApplication = dataOfClosedOfApplication;
        this.accountNumberForPaymentOfLoan = accountNumberForPaymentOfLoan;
        this.isPayouts = isPayouts;
        this.dateOfPayout = dateOfPayout;
        this.isClosed = isClosed;
    }

    public LoanApplicationsList(Customer customer, String employmentForm, BigDecimal incomeAmount, String employerName, String employerNipNumber, String employerAddress, String employerPhoneNumber, BigDecimal otherLiabilities, BigDecimal loanAmount, int repaymentPeriodInMonth, boolean isApplicationAccepted, LocalDate dateOfRegistrationOfApplication, LocalDate dataOfClosedOfApplication, String accountNumberForPaymentOfLoan, boolean isPayouts, LocalDate dateOfPayout, boolean isClosed) {
        this.customer = customer;
        this.employmentForm = employmentForm;
        this.incomeAmount = incomeAmount;
        this.employerName = employerName;
        this.employerNipNumber = employerNipNumber;
        this.employerAddress = employerAddress;
        this.employerPhoneNumber = employerPhoneNumber;
        this.otherLiabilities = otherLiabilities;
        this.loanAmount = loanAmount;
        this.repaymentPeriodInMonth = repaymentPeriodInMonth;
        this.isApplicationAccepted = isApplicationAccepted;
        this.dateOfRegistrationOfApplication = dateOfRegistrationOfApplication;
        this.dataOfClosedOfApplication = dataOfClosedOfApplication;
        this.accountNumberForPaymentOfLoan = accountNumberForPaymentOfLoan;
        this.isPayouts = isPayouts;
        this.dateOfPayout = dateOfPayout;
        this.isClosed = isClosed;
    }

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "APPLICATION_ID", unique = true)
    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    public Customer getCustomer() {
        return customer;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOAN_ID")
    public Loans getLoans() {
        return loans;
    }

    public String getEmploymentForm() {
        return employmentForm;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getEmployerNipNumber() {
        return employerNipNumber;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    public String getEmployerPhoneNumber() {
        return employerPhoneNumber;
    }

    public BigDecimal getOtherLiabilities() {
        return otherLiabilities;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public int getRepaymentPeriodInMonth() {
        return repaymentPeriodInMonth;
    }

    public boolean isApplicationAccepted() {
        return isApplicationAccepted;
    }

    public LocalDate getDateOfRegistrationOfApplication() {
        return dateOfRegistrationOfApplication;
    }

    public LocalDate getDataOfClosedOfApplication() {
        return dataOfClosedOfApplication;
    }

    public String getAccountNumberForPaymentOfLoan() {
        return accountNumberForPaymentOfLoan;
    }

    public boolean isPayouts() {
        return isPayouts;
    }

    public LocalDate getDateOfPayout() {
        return dateOfPayout;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setLoans(Loans loans) {
        this.loans = loans;
    }

    public void setEmploymentForm(String employmentForm) {
        this.employmentForm = employmentForm;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public void setEmployerNipNumber(String employerNipNumber) {
        this.employerNipNumber = employerNipNumber;
    }

    public void setEmployerAddress(String employerAddress) {
        this.employerAddress = employerAddress;
    }

    public void setEmployerPhoneNumber(String employerPhoneNumber) {
        this.employerPhoneNumber = employerPhoneNumber;
    }

    public void setOtherLiabilities(BigDecimal otherLiabilities) {
        this.otherLiabilities = otherLiabilities;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setRepaymentPeriodInMonth(int repaymentPeriodInMonth) {
        this.repaymentPeriodInMonth = repaymentPeriodInMonth;
    }

    public void setApplicationAccepted(boolean applicationAccepted) {
        isApplicationAccepted = applicationAccepted;
    }

    public void setDateOfRegistrationOfApplication(LocalDate dateOfRegistrationOfApplication) {
        this.dateOfRegistrationOfApplication = dateOfRegistrationOfApplication;
    }

    public void setDataOfClosedOfApplication(LocalDate dataOfClosedOfApplication) {
        this.dataOfClosedOfApplication = dataOfClosedOfApplication;
    }

    public void setAccountNumberForPaymentOfLoan(String accountNumberForPaymentOfLoan) {
        this.accountNumberForPaymentOfLoan = accountNumberForPaymentOfLoan;
    }

    public void setPayouts(boolean payouts) {
        isPayouts = payouts;
    }

    public void setDateOfPayout(LocalDate dateOfPayout) {
        this.dateOfPayout = dateOfPayout;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}

package com.example.takeloanapp.calculator;

import com.example.takeloanapp.domain.LoanApplicationsList;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.validator.LoanApplicationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class LoanCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanCalculator.class);
    @Autowired
    LoanApplicationValidator loanApplicationValidator;

    private static final BigDecimal ANNUAL_INTEREST_RATE = new BigDecimal("0.1980");
    private static final String LOAN_PRODUCT_NAME = "Summer Promotion 2021.";

    public BigDecimal calculateMonthlyInterestRate(LoanApplicationsList loansAppList){
        LOGGER.info("Starting calculating monthly interest rate for application.");
        BigDecimal monthlyInterestRate = ANNUAL_INTEREST_RATE.divide(new BigDecimal("12")).setScale(6,RoundingMode.HALF_UP);
        LOGGER.info("End of calculating monthly interest rate - is equal: '" + monthlyInterestRate + "' .");
        return  monthlyInterestRate;
    }

    public BigDecimal monthlyInterest(LoanApplicationsList loanApp, BigDecimal monthlyInterestRate){
        BigDecimal monthlyInterest = new BigDecimal(String.valueOf(loanApp.getLoanAmount().multiply(monthlyInterestRate))).setScale(2, RoundingMode.HALF_UP);
        LOGGER.info("...monthlyInterest: " + monthlyInterest);
        return monthlyInterest;
    }

    public BigDecimal monthlyCapital(LoanApplicationsList loanApp){
        BigDecimal periodInMonth = new BigDecimal(loanApp.getRepaymentPeriodInMonth()).setScale(2,RoundingMode.HALF_UP);
        LOGGER.info("...periodInMonth: " + periodInMonth);
        BigDecimal loanAmount = loanApp.getLoanAmount().setScale(2,RoundingMode.HALF_UP);
        LOGGER.info("...for loanAmount: " + loanAmount);
        BigDecimal monthlyCapital = loanAmount.divide(periodInMonth, 2, RoundingMode.HALF_UP);
        LOGGER.info("...monthlyCapital: " + monthlyCapital);
        return monthlyCapital;
    }

    public BigDecimal monthlyInstalment(LoanApplicationsList loanApp, BigDecimal monthlyInterestRate){
        LOGGER.info("Starting calculating whole amount of monthly payment......");
        BigDecimal monthlyCapital = monthlyCapital(loanApp);
        BigDecimal monthlyPayment = monthlyInterest(loanApp, monthlyInterestRate).add(monthlyCapital);
        LOGGER.info("...monthlyInstalment: " + monthlyPayment);
        return monthlyPayment;
    }

    public BigDecimal totalLoanPayments(LoanApplicationsList loanApp, BigDecimal monthlyPayment){
        BigDecimal periodInMonths = new BigDecimal(loanApp.getRepaymentPeriodInMonth());
        BigDecimal totalMonthlyPayments = monthlyPayment.multiply(periodInMonths);
        LOGGER.info("End of calculating TOTAL loan amount - is equal: " + totalMonthlyPayments);
        return totalMonthlyPayments.setScale(2,RoundingMode.CEILING);
    }

    public Loans setStaticDataToLoan(Loans loan){
        Loans responseLoan = loan;
        responseLoan.setProductName(LOAN_PRODUCT_NAME);
        responseLoan.setLoanRate(ANNUAL_INTEREST_RATE);
        return responseLoan;
    }
}
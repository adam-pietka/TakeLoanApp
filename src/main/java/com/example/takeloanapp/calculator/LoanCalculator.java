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
        BigDecimal monthlyInterestRate = ANNUAL_INTEREST_RATE.divide(new BigDecimal(loansAppList.getRepaymentPeriodInMonth()),10,RoundingMode.CEILING) ;
        LOGGER.info("End of calculating monthly interest rate - is equal: '" + monthlyInterestRate + "' .");
        return  monthlyInterestRate;
    }

    public BigDecimal monthlyPayment(LoanApplicationsList loanApp, BigDecimal monthlyInterestRate){
        LOGGER.info("Starting calculating monthly payment.");
        double loanAmount =  loanApp.getLoanAmount().doubleValue();
        double periodInMonth = loanApp.getRepaymentPeriodInMonth();
        double monthInterestRa = monthlyInterestRate.doubleValue();

        double result = loanAmount * monthInterestRa /
                (1 - 1 / Math.pow( 1+ monthInterestRa, periodInMonth));
        BigDecimal monthlyPayment = new BigDecimal(result).setScale(2, RoundingMode.CEILING);
        LOGGER.info("End of calculating monthly payment - is equal: " + monthlyPayment);
        boolean newCreditRate =
                loanApplicationValidator.simulationOfCredit(loanApp, monthlyPayment);
        LOGGER.info("End of simulation new credit rate is equal: " + newCreditRate);
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
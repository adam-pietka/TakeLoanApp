package com.example.takeloanapp.calculator;

import com.example.takeloanapp.domain.LoanApplicationsList;
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

    private static BigDecimal ANNUAL_INTEREST_RATE = new BigDecimal("0.0980");
    private static String LOAN_PRODUCT_NAME = "Summer Promotion 2021.";

    public BigDecimal calculateMonthlyInterestRate(LoanApplicationsList loansAppList){
        LOGGER.info("Starting calculating monthly interest rate. For application id: " + loansAppList.getId());
//        BigDecimal b = new BigDecimal("10");
        BigDecimal monthlyInterestRate = ANNUAL_INTEREST_RATE.divide(BigDecimal.TEN,10,RoundingMode.CEILING) ;
        System.out.println(" BIG DECIMAL: " +  monthlyInterestRate );
        LOGGER.info("End of calculating monthly interest rate - is equal: '" + monthlyInterestRate + "' .");
        monthlyPayment( loansAppList, monthlyInterestRate);
        return  monthlyInterestRate;
    }

    public BigDecimal monthlyPayment(LoanApplicationsList loanApp, BigDecimal monthlyInterestRate){
        LOGGER.info("Starting calculating monthly payment.");
        double loanAmount =  loanApp.getLoanAmount().doubleValue();
        double periodInMonth = loanApp.getRepaymentPeriodInMonth();
        double monthInterestRa = monthlyInterestRate.doubleValue();

        Double result = loanAmount * monthInterestRa /
                (1 - 1 / Math.pow( 1+ monthInterestRa, periodInMonth));
        BigDecimal monthlyPayment = new BigDecimal(result).setScale(2, RoundingMode.HALF_UP);
        LOGGER.info("End of calculating monthly payment - is equal: " + monthlyPayment);

        totalMonthlyPayments(loanApp, monthlyPayment);
        boolean newCreditRate =
                loanApplicationValidator.simulationOfCredit(loanApp, monthlyPayment);
        LOGGER.info("End of simulation new credit rate is equal: " + newCreditRate);
        return monthlyPayment;
    }

    public BigDecimal totalMonthlyPayments(LoanApplicationsList loanApp, BigDecimal monthlyPayment){
        BigDecimal periodInMonths = new BigDecimal(loanApp.getRepaymentPeriodInMonth());

        BigDecimal totalMonthlyPayments = monthlyPayment.multiply(periodInMonths);
        LOGGER.info("End of calculating TOTAL monthly payment - is equal: " + totalMonthlyPayments);

        return totalMonthlyPayments.setScale(2,RoundingMode.HALF_UP);
    }

}
package com.example.takeloanapp.validator;

import com.example.takeloanapp.domain.LoanApplicationsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LoanConditionsValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanConditionsValidator.class);

    private final BigDecimal MIN_AMOUNT = new BigDecimal("500.00");
    private final BigDecimal MAX_AMOUNT = new BigDecimal("120000.00");
    private final int MIN_PERIOD = 10;
    private final int MAX_PERIOD = 120;

    public boolean validLoanData(LoanApplicationsList loanAppl){
        LOGGER.info("Starting checking loan condition like: period / minimum amount and maximum amount.");
        boolean answerPeriod = true;
        boolean answerLoanAmount = true;
        if(periodValidator( loanAppl.getRepaymentPeriodInMonth())) answerPeriod= false;
        if(loanAmountValidator(loanAppl.getLoanAmount())) answerLoanAmount = false;

        LOGGER.info("Ended checking loan condition like: period / minimum amount and maximum amount: " + answerPeriod + "/" + answerLoanAmount + ". General: " + (answerPeriod && answerLoanAmount));
        return answerPeriod && answerLoanAmount;
    }

    public boolean periodValidator(int periodInMonth){
        LOGGER.info("Starting checking loan condition: period in month");
        if (periodInMonth < MIN_PERIOD){
            LOGGER.info("Period is to short, mini value is: " + MIN_PERIOD + ", requested is: " + periodInMonth);
            return true;
        }
        if (periodInMonth > MAX_PERIOD){
            LOGGER.info("Period is to LONG, max valu is: \" + MIN_PERIOD + \", requested is: \" + periodInMonth");
            return true;
        }
        LOGGER.info("Period is OK.");
        return false;
    }

    public boolean loanAmountValidator(BigDecimal loanAmount){
        LOGGER.info("Starting checking loan condition  minimum amount and maximum amount.");
        if (loanAmount.compareTo(MIN_AMOUNT) < 0){
            LOGGER.info("LOAN amount is to low, min amount is: " + MIN_AMOUNT + ", requested amount is: " + loanAmount);
            return true;
        }

        if (loanAmount.compareTo(MAX_AMOUNT) > 0){
            LOGGER.info("LOAN amount is to high, max amount is: " + MAX_AMOUNT + ", requested amount is: " + loanAmount);
            return true;
        }

        LOGGER.info("Requested loan amount is ok.");
        return false;
    }
}
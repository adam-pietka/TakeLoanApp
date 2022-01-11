package com.example.takeloanapp.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ArrearsCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArrearsCalculator.class);
    private static final BigDecimal ARREARS_DAILY_INTEREST_RATE = new BigDecimal("0.00055");

    public BigDecimal calculatingArrearsAmount(int numbersOfDays, BigDecimal amountOfLoan){
        BigDecimal numbersOfDaysBigDecimal = new BigDecimal(numbersOfDays);
        LOGGER.info("Starting calculating ArrearsAmount for " + numbersOfDaysBigDecimal + " numbers of delay days.");
        BigDecimal oustandingsAmount = ARREARS_DAILY_INTEREST_RATE.multiply(amountOfLoan).setScale(2, RoundingMode.HALF_UP);
        oustandingsAmount = oustandingsAmount.multiply(numbersOfDaysBigDecimal).setScale(2, RoundingMode.HALF_UP);
        LOGGER.info("Outstanding amount is: " + oustandingsAmount + " PLN.");
        return oustandingsAmount;
    }
}
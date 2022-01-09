package com.example.takeloanapp.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ArrearsCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArrearsCalculator.class);
    private static final BigDecimal ARREARS_DAILY_INTEREST_RATE = new BigDecimal("0.365");

    public void calculatingDailyArrearsAmount( int numbersOfDays){
        BigDecimal numbersOfDaysBigDecimal = new BigDecimal(numbersOfDays);
        LOGGER.info("Starting calculating monthly interest rate for application.\n*******\n*****" + numbersOfDaysBigDecimal);
        BigDecimal oustandingsAmount = numbersOfDaysBigDecimal.multiply(ARREARS_DAILY_INTEREST_RATE).setScale(2, RoundingMode.HALF_UP);
        LOGGER.info("Oustandings amount is: " + oustandingsAmount + " PLN.");
    }
}

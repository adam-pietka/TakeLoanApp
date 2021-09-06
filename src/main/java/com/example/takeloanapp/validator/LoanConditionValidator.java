package com.example.takeloanapp.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoanConditionValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanApplicationValidator.class);


    public void validLoanData(){
        LOGGER.info("Starting checking loan condition like, period minimum amount, maximum amount.");


    }
}

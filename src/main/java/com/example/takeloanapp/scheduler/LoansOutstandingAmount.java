package com.example.takeloanapp.scheduler;

import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.service.LoanArrearsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoansOutstandingAmount {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoansOutstandingAmount.class);

    @Autowired
    private LoanArrearsService loanArrearsService;

    @Scheduled(fixedDelay = 300_000) // == 10s = 10_000.
//    @Scheduled(cron = "0 50 23 * * ") //
    public void checkOutstandingAmount() throws LoanNotFoundException {
        LOGGER.info("Starting checking Outstanding Amount......");
        loanArrearsService.checkArrearsOnLoan();
    }
}

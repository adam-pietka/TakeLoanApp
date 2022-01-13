package com.example.takeloanapp.scheduler;

import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.service.LoanClosingProcessServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanClosingScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanClosingScheduler.class);

    @Autowired
    LoanClosingProcessServices loanClosingProcessServices;

    @Scheduled(fixedDelay = 120_000) // == 10s = 10_000.
//    @Scheduled(cron = "0 55 22 * * ") //
    public void checkLoansToBeClosed(){
        LOGGER.info("Scheduler LoanClosingScheduler is running...");
        try {
            loanClosingProcessServices.checkLoansToBeClosed();
        } catch (LoanNotFoundException e) {
            e.printStackTrace();
        }
    }
}

//package com.example.takeloanapp.scheduler;
//
//import com.example.takeloanapp.controller.exception.LoanNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@RequiredArgsConstructor
//public class ProcessScheduler {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessScheduler.class);
//
//    private  LoansOutstandingAmount loansOutstandingAmount;
//
//    //    @Scheduled(fixedDelay = 600_000) // == 10s = 10_000.
//    @Scheduled(cron = "0 1 9 * * MON-FRI") //
//    public void checkOutstandingsAmount() throws LoanNotFoundException {
//        LOGGER.info("starting process for outstanding amounts on loan...");
//        loansOutstandingAmount.checkOutstandingAmount();
//    }
//}

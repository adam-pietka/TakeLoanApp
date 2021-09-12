package com.example.takeloanapp.scheduler;

import com.example.takeloanapp.mail.MailContentTemplates;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailScheduler.class);

    private final MailContentTemplates mailContentTemplates;
    private final LoanRepaymentReminder loanRepaymentReminder;


        @Scheduled(fixedDelay = 600_000) // == 30s. = 30_000
//    @Scheduled(cron = "0 15 12 * * MON-FRI") //
    public void sendInformationEmail(){
            LOGGER.info("Staring....");
            System.out.println("********************************************" + // in this place
                    "\nSHEDULER MESSAGE ************************************" +
                    "\n********************************************");
    }

    @Scheduled(fixedDelay = 600_000) // == 10s = 10_000.
//    @Scheduled(cron = "0 1 9 * * MON-FRI") //
    public void sendInformationAboutAllLoans(){
        LOGGER.info("Staring send for: sendInformationAboutAllLoans");
        mailContentTemplates.getNumbersOfAllLoans();
    }

    @Scheduled(cron = "0 6 9 * * MON-FRI") //
    public void sendInformationAboutAllCustomers(){
        LOGGER.info("Staring send for: sendInformationAboutAllCustomers");
        mailContentTemplates.getNumbersOfAllCustomers();
    }

    @Scheduled(cron = "0 15 8 * * *") //
    public void sendRepaymentReminder(){
            LOGGER.info("Staring send for: sendRepaymentReminder");
            loanRepaymentReminder.repaymentInstalment();
    }
}
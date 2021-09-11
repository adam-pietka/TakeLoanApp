package com.example.takeloanapp.scheduler;

import com.example.takeloanapp.config.AdminConfig;
import com.example.takeloanapp.domain.Mail;
import com.example.takeloanapp.repository.LoanRepository;
import com.example.takeloanapp.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private final SimpleEmailService simpleEmailService;
    private final LoanRepository loanRepository;
    private final AdminConfig adminConfig;
    private static final String SUBJECT = "Tasks: Once a day email";

        @Scheduled(fixedDelay = 30000) // == 30s.
//    @Scheduled(cron = "0 15 12 * * MON-FRI") //
    public void sendInformationEmail(){
            System.out.println("********************************************" +
                    "\nSHEDULER MESSAGE ************************************" +
                    "\n********************************************");
//        simpleEmailService.send(new Mail( adminConfig.getAdminMail(),
//                SUBJECT,
//                messageContent(),
//                null
//                )
//        );
    }

    public String messageContent(){
        String basicTextMessage = "Currently in database you got: ";
        long size = loanRepository.count();

        if (size <= 1){
            return basicTextMessage + size + " loan.";
        } else {
            return basicTextMessage + size + " loans.";
        }
    }
}
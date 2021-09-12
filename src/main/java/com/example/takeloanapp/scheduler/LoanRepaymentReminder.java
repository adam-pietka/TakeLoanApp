package com.example.takeloanapp.scheduler;

import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.mail.MailContentTemplates;
import com.example.takeloanapp.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanRepaymentReminder {

    @Autowired
    private LoanService loanService;
    @Autowired
    private MailContentTemplates mailTemplates;

    public void repaymentInstalment(){
        List<Loans> loansList =  loanService.getAllLoans();
        loansList.stream()
                .filter(loans -> loans.getDayOfInstalmentRepayment() == LocalDate.now().plusDays(1).getDayOfMonth())
                .filter(loans -> loans.isActive())
                .forEach(i-> {
                    mailTemplates.sentRepaymentReminder(i.getCustomer().getMailAddress());
                });
    }
}

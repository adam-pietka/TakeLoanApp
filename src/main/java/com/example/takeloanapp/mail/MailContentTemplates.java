package com.example.takeloanapp.mail;

import com.example.takeloanapp.config.AdminConfig;
import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.Mail;
import com.example.takeloanapp.repository.CustomerRepository;
import com.example.takeloanapp.repository.LoanRepository;
import com.example.takeloanapp.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class MailContentTemplates {
    private static final String SUBJECT_REGISTRATION_CUSTOMER = "Welcome in out company.";
    private static final String SUBJECT_REGISTRATION_LOAN = "Information about loan.";
    private static final String SUBJECT_REPAYMENT_REMINDER = "Soft reminder, about loan repayment.";
    private static final String SUBJECT_FOR_CLOSED_LOAN = "Congratulations your loan has been closed.";
    private static final String BEST_REGARDS = "\n\nBest regards,\nBest 4 IT";


    private final SimpleEmailService emailService;
    private final AdminConfig adminConfig;
    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;


    public void sentMailForNewCustomer(Customer customer){
        String mailContent = "Dear " + customer.getName() + " " + customer.getSurname() + "," +
                "\nWelcome in our company. we just register you in our DB." + BEST_REGARDS;

        emailService.send( new Mail(customer.getMailAddress(), SUBJECT_REGISTRATION_CUSTOMER,
                mailContent, null));
    }

    public void sendMailAboutNewLoan(Loans loans){
        String mailContent = "Dear " + loans.getCustomer().getName() + "," +
                "\n\nCongratulation your loan has been accepted." + BEST_REGARDS;

        emailService.send( new Mail(loans.getCustomer().getMailAddress(), SUBJECT_REGISTRATION_LOAN,
                mailContent, null));
    }

    public void getNumbersOfAllLoans(){
        String basicTextMessage = "Currently in database you got: ";
        long size = loanRepository.count();
        if (size <= 1){
            basicTextMessage = basicTextMessage + size + " loan.";
        } else {
            basicTextMessage = basicTextMessage + size + " loans.";
        }
        emailService.send(new Mail(adminConfig.getAdminMail(), "Number of all loans in databases.",
                basicTextMessage, null));
    }

    public void getNumbersOfAllCustomers(){
        String basicTextMessage = "Currently in database you got: ";
        long size = customerRepository.count();
        if (size <= 1){
            basicTextMessage = basicTextMessage + size + " customer.";
        } else {
            basicTextMessage = basicTextMessage + size + " customers.";
        }
        emailService.send(new Mail(adminConfig.getAdminMail(), "Number of all customers in databases.",
                basicTextMessage, null));
    }

    public void sentRepaymentReminder(String mailAddress){
        String messageContent = "Hello," +
                "\n\nWe are glad that you are sill with us, we want to remind you about incoming instalment repayment." +
                "\nNext repayment will be at " + LocalDate.now().plusDays(1) + BEST_REGARDS;
        emailService.send(new Mail(mailAddress, SUBJECT_REPAYMENT_REMINDER,
                messageContent,null));

    }

    public void sentInformationAboutOverpaymentOnClosedLoan(String loanInformation){
        String messageContent = "Hi," +
                "\nwe just find closed loand with overpayment." +
                "\n" + loanInformation ;
        emailService.send(new Mail( adminConfig.getAdminMail(), "Overpayment on closed loan",
                messageContent, null));
    }

    public void sendCustomerMailAboutClosedLoan(String mailAddress){
        String messageContent = "Hello," +
                "\nCongratulation you payed off your loan." +
                "\n" + BEST_REGARDS;
        emailService.send(new Mail( mailAddress, SUBJECT_FOR_CLOSED_LOAN,
                messageContent, null));
    }

    public  void sentInformationAboutOutstandingsAmountOnClosedLoan(String loanInformation){
        String messageContent = "Hi," +
                "\nIt's not possible to close loan, because there is outstanding amount." +
                "\n" + loanInformation ;
        emailService.send(new Mail( adminConfig.getAdminMail(), "Arrears on loan which should be closed",
                messageContent, null));
    }
}
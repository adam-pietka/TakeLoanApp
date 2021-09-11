package com.example.takeloanapp.mail;

import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.domain.Mail;
import com.example.takeloanapp.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailContentTemplates {
    private static final String SUBJECT_REGISTRATION_CUSTOMER = "Welcome in out company.";

    @Autowired
    private SimpleEmailService emailService;

    public void contentForNewCustomer(Customer customer){
        String mailContent = "Dear " + customer.getName() + " " + customer.getSurname() + "," +
                "\nWelcome in our company. we just register you in our DB." +
                "\nBest regards," +
                "\nBest4 IT" ;

        emailService.send( new Mail(customer.getMailAddress(),
                SUBJECT_REGISTRATION_CUSTOMER,
                mailContent, null));
    }
}

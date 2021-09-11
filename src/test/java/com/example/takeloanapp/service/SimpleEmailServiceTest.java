package com.example.takeloanapp.service;

import com.example.takeloanapp.domain.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail(){
        //G
        Mail mail = new Mail("adi2-20@wp.pl", "Test TOPIC ooo", "Test Message\n TEST", "best4it.ap@gmail.com");
//        Mail mail = new Mail("best4it.ap@gmail.com", "Test Message","Lorem ipsum.... ", "adi2-2@wp.pl");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mailMessage.setCc(mail.getToCc());

        //W

        System.out.println(" ++++++ " + mail + " +++++++");
        simpleEmailService.send(mail);
        //T
         verify(javaMailSender, times(1)).send(mailMessage);
    }

}
package com.example.takeloanapp.service;

import com.example.takeloanapp.domain.Mail;
import com.example.takeloanapp.domain.NotificationHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleEmailService {
    private final JavaMailSender javaMailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    @Autowired
    private  NotificationHistoryService notificationService;

    public void send(final Mail mail) {
        LOGGER.info("Starting email preparation...");
        try {
            SimpleMailMessage mailMessage = createMailMessage(mail);
            javaMailSender.send(mailMessage);
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: " + e.getMessage(), e );
        }
    }

    private SimpleMailMessage createMailMessage(final  Mail mail){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        Optional.ofNullable(mail.getToCc())
                .ifPresent(isMail -> mailMessage.setCc(mail.getToCc()));

        NotificationHistory notificationHistory = new NotificationHistory();
        notificationHistory.setSendTimeStamp(LocalDateTime.now());
        notificationHistory.setNotyficationReceiver(mail.getMailTo());
        notificationHistory.setNotificationSubject(mail.getSubject());
        notificationHistory.setNotificationContent(mail.getMessage());
        Optional.ofNullable(mail.getToCc())
                .ifPresent(isCc -> notificationHistory.setNotificatinCcReceiver(mail.getToCc()));
        notificationService.saveNotification(notificationHistory);

        LOGGER.info("processing main in progress....");
        return mailMessage;
    }
}
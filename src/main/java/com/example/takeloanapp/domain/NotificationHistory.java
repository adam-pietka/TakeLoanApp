package com.example.takeloanapp.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTIFICATION_HISTOR")
public class NotificationHistory {

    private Long notificationId;
    private LocalDateTime sendTimeStamp;
    private String notyficationReceiver;
    private String notificationSubject;
    private String notificationContent;
    private String notificatinCcReceiver;

    public NotificationHistory() {
    }

    public NotificationHistory(Long notificationId, LocalDateTime sendTimeStamp, String notyficationReceiver, String notificationSubject, String notificationContent, String notificatinCcReceiver) {
        this.notificationId = notificationId;
        this.sendTimeStamp = sendTimeStamp;
        this.notyficationReceiver = notyficationReceiver;
        this.notificationSubject = notificationSubject;
        this.notificationContent = notificationContent;
        this.notificatinCcReceiver = notificatinCcReceiver;
    }

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "NOTIFICATION_ID", unique = true)
    public Long getNotificationId() {
        return notificationId;
    }

    public LocalDateTime getSendTimeStamp() {
        return sendTimeStamp;
    }

    public String getNotyficationReceiver() {
        return notyficationReceiver;
    }

    public String getNotificationSubject() {
        return notificationSubject;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public String getNotificatinCcReceiver() {
        return notificatinCcReceiver;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public void setSendTimeStamp(LocalDateTime sendTimeStamp) {
        this.sendTimeStamp = sendTimeStamp;
    }

    public void setNotyficationReceiver(String notyficationReceiver) {
        this.notyficationReceiver = notyficationReceiver;
    }

    public void setNotificationSubject(String notificationSubject) {
        this.notificationSubject = notificationSubject;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public void setNotificatinCcReceiver(String notificatinCcReceiver) {
        this.notificatinCcReceiver = notificatinCcReceiver;
    }
}

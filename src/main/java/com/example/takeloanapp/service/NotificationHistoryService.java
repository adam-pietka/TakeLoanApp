package com.example.takeloanapp.service;

import com.example.takeloanapp.domain.NotificationHistory;
import com.example.takeloanapp.repository.NotificationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationHistoryService {

    @Autowired
    private NotificationHistoryRepository notificationRepository;

    public NotificationHistory saveNotification(NotificationHistory notification){
        return notificationRepository.save(notification);
    }

    public List<NotificationHistory> getAllNotificationHistory(){
        return notificationRepository.findAll();
    }
    public void deleteNotificationById(Long notificationId){
        notificationRepository.deleteById(notificationId);
    }

}

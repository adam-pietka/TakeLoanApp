package com.example.takeloanapp.domain;

import com.example.takeloanapp.repository.NotificationHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class NotificationHistoryTestSuite{

    @Autowired
    NotificationHistoryRepository notiRepo;

    public NotificationHistory notiHistTest = new NotificationHistory(LocalDateTime.of(2022,01,30,10,30,58,525), "null@null.pl", "Test subject Once pone time...", "Lorem ipsum", "cc@address.pl");

    @Test
    void saveNotificationTest(){
//        G W
        NotificationHistory savedNoti =  notiRepo.save(notiHistTest);
//        T
        List<NotificationHistory> notificationHistoryList = notiRepo.findAll();
        assertEquals(1,notificationHistoryList.size());

//        clen up
        System.out.println("id: " + savedNoti.getNotificationId());
        notiRepo.deleteById(savedNoti.getNotificationId());
    }

    @Test
    void findByIdTest(){
//        G W
        Long notificationId = notiRepo.save(notiHistTest).getNotificationId();
//        T
        Optional<NotificationHistory> foundNoti =  notiRepo.findById(notificationId);
        assertEquals("Test subject Once pone time...", foundNoti.get().getNotificationSubject());
//        clean up
        notiRepo.deleteById(notificationId);
    }

    @Test
    void finAllTest(){
//        G
        notiRepo.save(notiHistTest);
        NotificationHistory notiHistTestSecond = new NotificationHistory(LocalDateTime.of(2022,01,30,10,30,58,525), "null2@null2.pl", "Test subject Department of ...", "Lorem ipsum etc.", "cc2@address.pl");
        notiRepo.save(notiHistTestSecond);
//        W
        List<NotificationHistory> notificationHistoryList = notiRepo.findAll();
//        T
        assertEquals(2, notificationHistoryList.size());
//        clen up
        notiRepo.deleteById(notiHistTest.getNotificationId());
        notiRepo.deleteById(notiHistTestSecond.getNotificationId());

    }
    @Test
    void deleteByIdTest(){
//        G W
        NotificationHistory savedRecord = notiRepo.save(notiHistTest);
        List<NotificationHistory> notificationHistoryList = notiRepo.findAll();
//        T
        assertEquals(1, notificationHistoryList.size());
//        W
        notiRepo.deleteById(savedRecord.getNotificationId());
        List<NotificationHistory> notificationHistoryListAfterDel = notiRepo.findAll();
//        T
        assertEquals(0 , notificationHistoryListAfterDel.size());
    }
}
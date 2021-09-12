package com.example.takeloanapp.repository;

import com.example.takeloanapp.domain.NotificationHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface NotificationHistoryRepository extends CrudRepository<NotificationHistory, Long> {


    NotificationHistory save(NotificationHistory notificationHistory);

    List<NotificationHistory> findAll();

    @Override
    Optional<NotificationHistory> findById(Long aLong);

    void deleteById(Long id);

}

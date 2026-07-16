package com.example.MoneyManagement.Repository;

import com.example.MoneyManagement.Entity.NotificationEntity;
import com.example.MoneyManagement.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByUserOrderByCreatedAtDesc(UserEntity user);
    List<NotificationEntity> findByUserAndIsReadFalseOrderByCreatedAtDesc(UserEntity user);
    long countByUserAndIsReadFalse(UserEntity user);
    List<NotificationEntity> findTop10ByUserOrderByCreatedAtDesc(UserEntity user);

}
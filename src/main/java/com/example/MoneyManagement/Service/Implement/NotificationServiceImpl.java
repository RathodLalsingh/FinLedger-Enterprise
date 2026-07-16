package com.example.MoneyManagement.Service.Implement;

import com.example.MoneyManagement.Dtos.NotificationResponseDto;
import com.example.MoneyManagement.Entity.NotificationEntity;
import com.example.MoneyManagement.Entity.UserEntity;
import com.example.MoneyManagement.Exception.ResourceNotFoundException;
import com.example.MoneyManagement.Mapper.NotificationMapper;
import com.example.MoneyManagement.Repository.NotificationRepository;
import com.example.MoneyManagement.Repository.UserRepository;
import com.example.MoneyManagement.Service.NotificationService;
import com.example.MoneyManagement.Util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    private UserEntity getCurrentUser() {
        String email = SecurityUtil.getCurrentUserEmail();
        if (email == null || email.isBlank()) {
            throw new ResourceNotFoundException(
                    "Authenticated user not found."
            );
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + email
                        ));
    }
    @Override
    public List<NotificationResponseDto> getAllNotification() {
        log.info("Fetching all notifications.");
        UserEntity user = getCurrentUser();
        List<NotificationEntity> notifications =
                notificationRepository.findByUserOrderByCreatedAtDesc(user);
        log.info("Fetched {} notification(s).", notifications.size());
        return NotificationMapper.toResponseDtoList(notifications);
    }
    @Override
    public List<NotificationResponseDto> getUnreadNotifications() {
        log.info("Fetching unread notifications.");
        UserEntity user = getCurrentUser();
        List<NotificationEntity> notifications =
                notificationRepository
                        .findByUserAndIsReadFalseOrderByCreatedAtDesc(user);
        log.info("Fetched {} unread notification(s).", notifications.size());
        return NotificationMapper.toResponseDtoList(notifications);
    }
    @Override
    public Long getUnreadNotificationCount() {
        log.info("Counting unread notifications.");
        UserEntity user = getCurrentUser();
        long count =
                notificationRepository.countByUserAndIsReadFalse(user);
        log.info("Unread notification count: {}", count);
        return count;
    }
}
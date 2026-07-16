package com.example.MoneyManagement.Service;

import com.example.MoneyManagement.Dtos.NotificationResponseDto;
import java.util.List;

public interface NotificationService {

    List<NotificationResponseDto> getAllNotification();
    List<NotificationResponseDto> getUnreadNotifications();
    Long getUnreadNotificationCount();

}
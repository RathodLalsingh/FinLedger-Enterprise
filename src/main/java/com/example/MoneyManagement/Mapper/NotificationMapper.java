package com.example.MoneyManagement.Mapper;

import com.example.MoneyManagement.Dtos.NotificationResponseDto;
import com.example.MoneyManagement.Entity.NotificationEntity;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class NotificationMapper {
    public static NotificationResponseDto toResponseDto(NotificationEntity entity) {

        if (entity == null) {
            return null;
        }
        return NotificationResponseDto.builder()
                .id(entity.getId())
                .message(entity.getMessage())
                .isRead(entity.isRead())
                .createdAt(entity.getCreatedAt())
                .build();
    }
    public static List<NotificationResponseDto> toResponseDtoList(
            List<NotificationEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(NotificationMapper::toResponseDto)
                .collect(Collectors.toList());
    }
    public static NotificationEntity copy(NotificationEntity source) {

        if (source == null) {
            return null;
        }
        return NotificationEntity.builder()
                .id(source.getId())
                .message(source.getMessage())
                .isRead(source.isRead())
                .createdAt(source.getCreatedAt())
                .user(source.getUser())
                .build();
    }
}

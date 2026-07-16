package com.example.MoneyManagement.Mapper;

import com.example.MoneyManagement.Dtos.CategoryRequestDto;
import com.example.MoneyManagement.Dtos.CategoryResponseDto;
import com.example.MoneyManagement.Entity.CategoryEntity;
import lombok.experimental.UtilityClass;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CategoryMapper {
    public static CategoryEntity toEntity(CategoryRequestDto request) {

        if (request == null) {
            return null;
        }

        return CategoryEntity.builder()
                .name(request.getName())
                .type(request.getType())
                .build();
    }

    public static CategoryResponseDto toResponseDto(
            CategoryEntity entity) {

        if (entity == null) {
            return null;
        }
        return CategoryResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .build();
    }

    public static void updateEntity(
            CategoryEntity entity,
            CategoryRequestDto request) {

        if (entity == null || request == null) {
            return;
        }

        if (request.getName() != null &&
                !request.getName().trim().isEmpty()) {
            entity.setName(request.getName().trim());
        }

        if (request.getType() != null &&
                !request.getType().trim().isEmpty()) {

            entity.setType(request.getType().trim());
        }
    }
    public static List<CategoryResponseDto> toResponseDtoList(
            List<CategoryEntity> entities) {
        List<CategoryResponseDto> responseList = new ArrayList<>();

        if (entities == null || entities.isEmpty()) {
            return responseList;
        }

        for (CategoryEntity entity : entities) {
            responseList.add(toResponseDto(entity));
        }

        return responseList;
    }

    public static boolean isValid(CategoryEntity entity) {
        return entity != null;
    }
    public static boolean isEmpty(
            List<CategoryEntity> entities) {
        return entities == null || entities.isEmpty();
    }

    public static CategoryResponseDto emptyResponse() {

        return CategoryResponseDto.builder()
                .id(null)
                .name("")
                .type("")
                .build();
    }

    public static CategoryEntity copy(CategoryEntity entity) {

        if (entity == null) {
            return null;
        }

        return CategoryEntity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
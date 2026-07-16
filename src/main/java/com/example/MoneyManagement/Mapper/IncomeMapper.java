package com.example.MoneyManagement.Mapper;

import com.example.MoneyManagement.Dtos.IncomeRequestDto;
import com.example.MoneyManagement.Dtos.IncomeResponseDto;
import com.example.MoneyManagement.Entity.CategoryEntity;
import com.example.MoneyManagement.Entity.IncomeEntity;
import lombok.experimental.UtilityClass;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class IncomeMapper {

    public static IncomeEntity toEntity(
            IncomeRequestDto request) {
        if (request == null) {
            return null;
        }
        return IncomeEntity.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .incomeDate(request.getIncomeDate())
                .build();
    }

    public static IncomeResponseDto toResponseDto(
            IncomeEntity entity) {
        if (entity == null) {
            return null;
        }
        Long categoryId = null;
        String categoryName = null;

        CategoryEntity category = entity.getCategory();

        if (category != null) {
            categoryId = category.getId();
            categoryName = category.getName();
        }

        return IncomeResponseDto.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .incomeDate(entity.getIncomeDate())
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build();
    }

    public static void updateEntity(
            IncomeEntity entity,
            IncomeRequestDto dto) {

        if (entity == null || dto == null) {
            return;
        }

        entity.setAmount(dto.getAmount());
        entity.setDescription(dto.getDescription());
        entity.setIncomeDate(dto.getIncomeDate());

    }

    public static List<IncomeResponseDto> toResponseDtoList(
            List<IncomeEntity> incomeEntities) {

        if (incomeEntities == null || incomeEntities.isEmpty()) {
            return Collections.emptyList();
        }
        return incomeEntities.stream()
                .map(IncomeMapper::toResponseDto)
                .collect(Collectors.toList());
    }
    public static IncomeEntity copy(IncomeEntity source) {

        if (source == null) {
            return null;
        }

        return IncomeEntity.builder()
                .id(source.getId())
                .amount(source.getAmount())
                .description(source.getDescription())
                .incomeDate(source.getIncomeDate())
                .createdAt(source.getCreatedAt())
                .category(source.getCategory())
                .user(source.getUser())
                .build();
    }
    private static String getCategoryName(CategoryEntity category) {

        if (category == null) {
            return null;
        }

        return category.getName();
    }
    private static Long getCategoryId(CategoryEntity category) {

        if (category == null) {
            return null;
        }
        return category.getId();
    }

}

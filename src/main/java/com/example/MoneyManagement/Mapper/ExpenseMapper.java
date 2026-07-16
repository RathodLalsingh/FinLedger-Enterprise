package com.example.MoneyManagement.Mapper;

import com.example.MoneyManagement.Dtos.ExpenseRequestDto;
import com.example.MoneyManagement.Dtos.ExpenseResponseDto;
import com.example.MoneyManagement.Entity.CategoryEntity;
import com.example.MoneyManagement.Entity.ExpenseEntity;
import lombok.experimental.UtilityClass;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@UtilityClass
public class ExpenseMapper {

    public static ExpenseEntity toEntity(
            ExpenseRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return ExpenseEntity.builder()
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .expenseDate(dto.getExpenseDate())
                .build();
    }
    public static ExpenseResponseDto toResponseDto(
            ExpenseEntity entity) {

        if (entity == null) {
            return null;
        }
        return ExpenseResponseDto.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .expenseDate(entity.getExpenseDate())
                .categoryId(getCategoryId(entity.getCategory()))
                .categoryName(getCategoryName(entity.getCategory()))
                .build();
    }

    public static void updateEntity(
            ExpenseEntity entity,
            ExpenseRequestDto dto) {

        if (entity == null || dto == null) {
            return;
        }
        entity.setAmount(dto.getAmount());
        entity.setDescription(dto.getDescription());
        entity.setExpenseDate(dto.getExpenseDate());
    }
    public static List<ExpenseResponseDto> toResponseDtoList(
            List<ExpenseEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(ExpenseMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public static ExpenseEntity copy(ExpenseEntity source) {
        if (source == null) {
            return null;
        }
        return ExpenseEntity.builder()
                .id(source.getId())
                .amount(source.getAmount())
                .description(source.getDescription())
                .expenseDate(source.getExpenseDate())
                .createdAt(source.getCreatedAt())
                .category(source.getCategory())
                .user(source.getUser())
                .build();
    }
    private static Long getCategoryId(CategoryEntity category) {
        if (category == null) {
            return null;
        }
        return category.getId();
    }
    private static String getCategoryName(CategoryEntity category) {
        if (category == null) {
            return null;
        }
        return category.getName();
    }

}
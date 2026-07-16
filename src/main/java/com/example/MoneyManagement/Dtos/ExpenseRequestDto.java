package com.example.MoneyManagement.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestDto {

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be greater tha zero")
    private BigDecimal amount;

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "expense date id required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expenseDate;

    @NotNull(message = "categoryId is required")
    private Long categoryId;

}

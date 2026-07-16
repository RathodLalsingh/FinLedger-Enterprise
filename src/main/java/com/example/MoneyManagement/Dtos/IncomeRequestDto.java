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
public class IncomeRequestDto {

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "income date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate incomeDate;

    @NotNull(message = "category id is required")
    private Long categoryId;
}

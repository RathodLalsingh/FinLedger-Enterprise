package com.example.MoneyManagement.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DashboardResponseDto {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal totalBalance;
    private Long totalCategories;
    private Long totalTransactions;
}

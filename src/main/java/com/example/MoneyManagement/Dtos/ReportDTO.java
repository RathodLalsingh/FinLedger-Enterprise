package com.example.MoneyManagement.Dtos;

import com.example.MoneyManagement.Entity.ExpenseEntity;
import com.example.MoneyManagement.Entity.IncomeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    private String userName;

    private String email;

    private List<IncomeEntity> incomes;

    private List<ExpenseEntity> expenses;

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private BigDecimal savings;

}
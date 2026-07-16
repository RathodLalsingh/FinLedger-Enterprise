package com.example.MoneyManagement.Service;

import com.example.MoneyManagement.Dtos.ExpenseRequestDto;
import com.example.MoneyManagement.Dtos.ExpenseResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    ExpenseResponseDto addExpense(ExpenseRequestDto requestDto);
    List<ExpenseResponseDto> getAllExpenses();
    ExpenseResponseDto getExpenseById(Long id);
    ExpenseResponseDto updateExpense(
            Long id,
            ExpenseRequestDto requestDto
    );
    void deleteExpense(Long id);
    List<ExpenseResponseDto> getExpensesBetweenDates(
            LocalDate startDate,
            LocalDate endDate
    );
    List<ExpenseResponseDto> getLatestExpenses();
}
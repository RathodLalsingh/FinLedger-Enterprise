package com.example.MoneyManagement.Service;

import com.example.MoneyManagement.Dtos.IncomeRequestDto;
import com.example.MoneyManagement.Dtos.IncomeResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface IncomeService  {
    IncomeResponseDto addIncome(IncomeRequestDto requestDto);
    List<IncomeResponseDto> getAllIncome();
    IncomeResponseDto getIncomeById(Long id);
    IncomeResponseDto updateIncome(
            Long id,
            IncomeRequestDto requestDto
    );
    void deleteIncome(Long id);
    List<IncomeResponseDto> getIncomeBetweenDates(
            LocalDate startDate,
            LocalDate endDate
    );
    List<IncomeResponseDto> getLatestIncome();
}

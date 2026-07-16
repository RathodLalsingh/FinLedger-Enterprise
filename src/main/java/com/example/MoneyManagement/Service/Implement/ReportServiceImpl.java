package com.example.MoneyManagement.Service.Implement;

import com.example.MoneyManagement.Dtos.ReportDTO;
import com.example.MoneyManagement.Entity.ExpenseEntity;
import com.example.MoneyManagement.Entity.IncomeEntity;
import com.example.MoneyManagement.Entity.UserEntity;
import com.example.MoneyManagement.Repository.ExpenseRepository;
import com.example.MoneyManagement.Repository.IncomeRepository;
import com.example.MoneyManagement.Service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    @Override
    public ReportDTO generateUserReport(UserEntity user) {
        List<IncomeEntity> incomes =
                incomeRepository.findByUser(user);
        List<ExpenseEntity> expenses =
                expenseRepository.findByUser(user);

        BigDecimal totalIncome =
                incomes.stream()
                        .map(IncomeEntity::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense =
                expenses.stream()
                        .map(ExpenseEntity::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal savings =
                totalIncome.subtract(totalExpense);

        return ReportDTO.builder()
                .userName(user.getName())
                .email(user.getEmail())
                .incomes(incomes)
                .expenses(expenses)
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .savings(savings)
                .build();
    }
    @Override
    public byte[] generateDailyReport(UserEntity user) {
        return new byte[0];
    }
}

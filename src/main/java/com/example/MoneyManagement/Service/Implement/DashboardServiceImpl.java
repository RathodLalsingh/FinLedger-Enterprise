package com.example.MoneyManagement.Service.Implement;

import com.example.MoneyManagement.Dtos.DashboardResponseDto;
import com.example.MoneyManagement.Entity.UserEntity;
import com.example.MoneyManagement.Exception.ResourceNotFoundException;
import com.example.MoneyManagement.Repository.CategoryRepository;
import com.example.MoneyManagement.Repository.ExpenseRepository;
import com.example.MoneyManagement.Repository.IncomeRepository;
import com.example.MoneyManagement.Repository.UserRepository;
import com.example.MoneyManagement.Service.DashboardService;
import com.example.MoneyManagement.Util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private UserEntity getCurrentUser() {
        String email = SecurityUtil.getCurrentUserEmail();
        if (email == null || email.isBlank()) {
            throw new ResourceNotFoundException(
                    "Authenticated user not found."
            );
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email : " + email
                        ));
    }


    @Override
    public DashboardResponseDto getDashboardSummary() {
        log.info("Fetching dashboard summary.");
        UserEntity user = getCurrentUser();
        BigDecimal totalIncome = incomeRepository.getTotalIncome(user);
        BigDecimal totalExpense = expenseRepository.getTotalExpense(user);
        BigDecimal totalBalance = totalIncome.subtract(totalExpense);

        Long totalCategories =
                (long) categoryRepository.findByUser(user).size();
        Long totalIncomeTransactions =
                (long) incomeRepository.findByUser(user).size();
        Long totalExpenseTransactions =
                (long) expenseRepository.findByUser(user).size();
        Long totalTransactions =
                totalIncomeTransactions + totalExpenseTransactions;
        log.info("Dashboard summary generated successfully.");

        return DashboardResponseDto.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .totalBalance(totalBalance)
                .totalCategories(totalCategories)
                .totalTransactions(totalTransactions)
                .build();
    }
}
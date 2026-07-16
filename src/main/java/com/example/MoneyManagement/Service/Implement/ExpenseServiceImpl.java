package com.example.MoneyManagement.Service.Implement;

import com.example.MoneyManagement.Dtos.ExpenseRequestDto;
import com.example.MoneyManagement.Dtos.ExpenseResponseDto;
import com.example.MoneyManagement.Entity.CategoryEntity;
import com.example.MoneyManagement.Entity.ExpenseEntity;
import com.example.MoneyManagement.Entity.NotificationEntity;
import com.example.MoneyManagement.Entity.UserEntity;
import com.example.MoneyManagement.Exception.ResourceNotFoundException;
import com.example.MoneyManagement.Mapper.ExpenseMapper;
import com.example.MoneyManagement.Repository.CategoryRepository;
import com.example.MoneyManagement.Repository.ExpenseRepository;
import com.example.MoneyManagement.Repository.NotificationRepository;
import com.example.MoneyManagement.Repository.UserRepository;
import com.example.MoneyManagement.Service.ExpenseService;
import com.example.MoneyManagement.Util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    private UserEntity getCurrentUser() {
        String email = SecurityUtil.getCurrentUserEmail();
        if (email == null || email.isBlank()) {
            throw new ResourceNotFoundException("Authenticated user not found.");
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email : " + email));
    }
    private CategoryEntity getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id : " + categoryId));
    }

    private ExpenseEntity getExpenseByIdAndUser(Long expenseId) {
        UserEntity currentUser = getCurrentUser();
        ExpenseEntity expense = expenseRepository.findById(expenseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense not found with id : " + expenseId));

        if (!expense.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException(
                    "Expense not found with id : " + expenseId);
        }
        return expense;
    }
    @Override
    public ExpenseResponseDto addExpense(ExpenseRequestDto requestDto) {

        log.info("Creating expense transaction.");
        UserEntity currentUser = getCurrentUser();
        CategoryEntity category = getCategoryById(requestDto.getCategoryId());
        if (!category.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException(
                    "Category does not belong to the authenticated user."
            );
        }

        if (!"EXPENSE".equalsIgnoreCase(category.getType())) {
            throw new ResourceNotFoundException(
                    "Selected category is not an EXPENSE category."
            );
        }
        ExpenseEntity expense = ExpenseMapper.toEntity(requestDto);
        expense.setUser(currentUser);
        expense.setCategory(category);

        ExpenseEntity savedExpense = expenseRepository.save(expense);
        NotificationEntity notification = NotificationEntity.builder()
                .message("Expense added successfully. Amount: ₹" + savedExpense.getAmount())
                .user(currentUser)
                .build();

        notificationRepository.save(notification);
        log.info("========== EXPENSE SAVED ==========");
        log.info("Expense ID      : {}", savedExpense.getId());
        log.info("User ID         : {}", currentUser.getId());
        log.info("User Email      : {}", currentUser.getEmail());
        log.info("Category        : {}", category.getName());
        log.info("Amount          : {}", savedExpense.getAmount());
        log.info("===================================");

        return ExpenseMapper.toResponseDto(savedExpense);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDto> getAllExpenses() {

        log.info("Fetching all expenses for authenticated user.");
        UserEntity currentUser = getCurrentUser();
        List<ExpenseEntity> expenses = expenseRepository.findByUser(currentUser);
        log.info("Retrieved {} expense(s).", expenses.size());
        return ExpenseMapper.toResponseDtoList(expenses);
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseResponseDto getExpenseById(Long id) {
        log.info("Fetching expense with id: {}", id);
        ExpenseEntity expense = getExpenseByIdAndUser(id);
        return ExpenseMapper.toResponseDto(expense);
    }
    @Override
    public ExpenseResponseDto updateExpense(
            Long id,
            ExpenseRequestDto requestDto) {
        log.info("Updating expense transaction with id: {}", id);
        UserEntity currentUser = getCurrentUser();
        ExpenseEntity expense = getExpenseByIdAndUser(id);
        CategoryEntity category = getCategoryById(requestDto.getCategoryId());

        if (!category.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException(
                    "Category does not belong to the authenticated user."
            );
        }

        if (!"EXPENSE".equalsIgnoreCase(category.getType())) {
            throw new ResourceNotFoundException(
                    "Selected category is not an EXPENSE category."
            );
        }
        ExpenseMapper.updateEntity(expense, requestDto);
        expense.setCategory(category);
        ExpenseEntity updatedExpense = expenseRepository.save(expense);
        NotificationEntity notification = NotificationEntity.builder()
                .message("Expense updated successfully. Amount: ₹"
                        + updatedExpense.getAmount())
                .user(currentUser)
                .build();
        notificationRepository.save(notification);

        log.info("========== EXPENSE UPDATED ==========");
        log.info("Expense ID      : {}", updatedExpense.getId());
        log.info("User ID         : {}", currentUser.getId());
        log.info("User Email      : {}", currentUser.getEmail());
        log.info("Category        : {}", category.getName());
        log.info("Amount          : {}", updatedExpense.getAmount());
        log.info("Notification Saved Successfully");
        log.info("====================================");

        return ExpenseMapper.toResponseDto(updatedExpense);
    }

    @Override
    public void deleteExpense(Long id) {
        log.info("Deleting expense transaction with id: {}", id);
        UserEntity currentUser = getCurrentUser();
        ExpenseEntity expense = getExpenseByIdAndUser(id);
        String amount = expense.getAmount().toString();
        NotificationEntity notification = NotificationEntity.builder()
                .message("Expense deleted successfully. Amount: ₹" + amount)
                .user(currentUser)
                .build();
        notificationRepository.save(notification);
        expenseRepository.delete(expense);

        log.info("==================================");
        log.info("Expense Deleted Successfully");
        log.info("Expense ID      : {}", expense.getId());
        log.info("User ID         : {}", currentUser.getId());
        log.info("User Email      : {}", currentUser.getEmail());
        log.info("Amount          : {}", amount);
        log.info("Notification Saved Successfully");
        log.info("==================================");
    }
    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDto> getExpensesBetweenDates(
            LocalDate startDate,
            LocalDate endDate) {

        log.info(
                "Fetching expense transactions from {} to {}",
                startDate,
                endDate
        );
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException(
                    "Start date and End date are required."
            );
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "Start date cannot be after End date."
            );
        }

        UserEntity currentUser = getCurrentUser();

        List<ExpenseEntity> expenses =
                expenseRepository.findByUserAndExpenseDateBetween(
                        currentUser,
                        startDate,
                        endDate
                );

        log.info(
                "Found {} expense transaction(s).",
                expenses.size()
        );

        return ExpenseMapper.toResponseDtoList(expenses);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDto> getLatestExpenses() {

        log.info("Fetching latest 10 expense transactions.");

        UserEntity currentUser = getCurrentUser();

        List<ExpenseEntity> expenses =
                expenseRepository.findTop10ByUserOrderByExpenseDateDesc(
                        currentUser
                );

        log.info(
                "Latest expense records found: {}",
                expenses.size()
        );
        return ExpenseMapper.toResponseDtoList(expenses);
    }

}
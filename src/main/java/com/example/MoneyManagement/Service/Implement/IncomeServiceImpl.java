package com.example.MoneyManagement.Service.Implement;


import com.example.MoneyManagement.Entity.NotificationEntity;
import com.example.MoneyManagement.Repository.NotificationRepository;
import com.example.MoneyManagement.Dtos.IncomeRequestDto;
import com.example.MoneyManagement.Dtos.IncomeResponseDto;
import com.example.MoneyManagement.Entity.CategoryEntity;
import com.example.MoneyManagement.Entity.IncomeEntity;
import com.example.MoneyManagement.Entity.UserEntity;
import com.example.MoneyManagement.Exception.ResourceNotFoundException;
import com.example.MoneyManagement.Mapper.IncomeMapper;
import com.example.MoneyManagement.Repository.CategoryRepository;
import com.example.MoneyManagement.Repository.IncomeRepository;
import com.example.MoneyManagement.Repository.UserRepository;
import com.example.MoneyManagement.Service.IncomeService;
import com.example.MoneyManagement.Util.SecurityUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Data
@Service
@RequiredArgsConstructor
@Transactional
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

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
    private CategoryEntity getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id : " + categoryId
                        ));
    }


    private IncomeEntity getIncomeByIdAndUser(
            Long incomeId,
            UserEntity user) {

        IncomeEntity income = incomeRepository.findById(incomeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Income not found with id : " + incomeId
                        ));

        if (!income.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException(
                    "Income does not belong to the authenticated user."
            );
        }
        return income;
    }

@Override
public IncomeResponseDto addIncome(IncomeRequestDto requestDto) {
    log.info("Creating income transaction.");
    UserEntity user = getCurrentUser();
    CategoryEntity category = getCategoryById(requestDto.getCategoryId());
    if (!category.getUser().getId().equals(user.getId())) {
        throw new ResourceNotFoundException(
                "Category does not belong to the authenticated user."
        );
    }

    if (!"INCOME".equalsIgnoreCase(category.getType())) {
        throw new ResourceNotFoundException(
                "Selected category is not an INCOME category."
        );
    }
    IncomeEntity income = IncomeMapper.toEntity(requestDto);
    income.setUser(user);
    income.setCategory(category);
    IncomeEntity savedIncome = incomeRepository.save(income);
    log.info("Income saved successfully with id {}", savedIncome.getId());

    NotificationEntity notification = NotificationEntity.builder()
            .message("Income added successfully. Amount: ₹" + savedIncome.getAmount())
            .user(user)
            .build();
    notificationRepository.save(notification);
    log.info("Notification created successfully.");

    log.info("=========================================");
    log.info("Income ID      : {}", savedIncome.getId());
    log.info("User           : {}", user.getEmail());
    log.info("Category       : {}", category.getName());
    log.info("Amount         : {}", savedIncome.getAmount());
    log.info("Notification Saved Successfully");
    log.info("=========================================");
    return IncomeMapper.toResponseDto(savedIncome);
}

    @Override
    @Transactional(readOnly = true)
    public List<IncomeResponseDto> getAllIncome() {

        log.info("Fetching all income transactions.");

        UserEntity user = getCurrentUser();

        List<IncomeEntity> incomes = incomeRepository.findByUser(user);

        log.info("Total income transactions found: {}", incomes.size());

        return IncomeMapper.toResponseDtoList(incomes);
    }


    @Override
    @Transactional(readOnly = true)
    public IncomeResponseDto getIncomeById(Long id) {

        log.info("Fetching income transaction with id: {}", id);

        UserEntity user = getCurrentUser();

        IncomeEntity income = getIncomeByIdAndUser(id, user);

        return IncomeMapper.toResponseDto(income);
    }

    @Override
    public IncomeResponseDto updateIncome(
            Long id,
            IncomeRequestDto requestDto) {
        log.info("Updating income transaction with id: {}", id);
        UserEntity user = getCurrentUser();
        IncomeEntity income = getIncomeByIdAndUser(id, user);
        CategoryEntity category = getCategoryById(requestDto.getCategoryId());
        if (!category.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException(
                    "Category does not belong to the authenticated user."
            );
        }

        if (!"INCOME".equalsIgnoreCase(category.getType())) {
            throw new ResourceNotFoundException(
                    "Selected category is not an INCOME category."
            );
        }

        IncomeMapper.updateEntity(income, requestDto);
        income.setCategory(category);
        IncomeEntity updatedIncome = incomeRepository.save(income);

        NotificationEntity notification = NotificationEntity.builder()
                .message("Income updated successfully. Amount: ₹"
                        + updatedIncome.getAmount())
                .user(user)
                .build();

        notificationRepository.save(notification);

        log.info("=================================");
        log.info("Income Updated Successfully");
        log.info("Income ID      : {}", updatedIncome.getId());
        log.info("User           : {}", user.getEmail());
        log.info("Category       : {}", category.getName());
        log.info("Amount         : {}", updatedIncome.getAmount());
        log.info("Notification Saved");
        log.info("=================================");
        return IncomeMapper.toResponseDto(updatedIncome);
    }

    @Override
    public void deleteIncome(Long id) {
        log.info("Deleting income transaction with id: {}", id);
        UserEntity user = getCurrentUser();
        IncomeEntity income = getIncomeByIdAndUser(id, user);
        String amount = income.getAmount().toString();
        NotificationEntity notification = NotificationEntity.builder()
                .message("Income deleted successfully. Amount: ₹" + amount)
                .user(user)
                .build();

        notificationRepository.save(notification);
        incomeRepository.delete(income);

        log.info("=================================");
        log.info("Income Deleted Successfully");
        log.info("Income ID      : {}", income.getId());
        log.info("User           : {}", user.getEmail());
        log.info("Amount         : {}", amount);
        log.info("Notification Saved");
        log.info("=================================");
    }

    @Transactional(readOnly = true)
    public List<IncomeResponseDto> getIncomeBetweenDates(
            LocalDate startDate,
            LocalDate endDate) {
        log.info(
                "Fetching income transactions from {} to {}",
                startDate,
                endDate
        );
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException(
                    "Start date and end date must not be null."
            );
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "Start date cannot be after end date."
            );
        }
        UserEntity user = getCurrentUser();
        List<IncomeEntity> incomes =
                incomeRepository.findByUserAndIncomeDateBetween(
                        user,
                        startDate,
                        endDate
                );

        log.info(
                "Found {} income transactions.",
                incomes.size()
        );

        return IncomeMapper.toResponseDtoList(incomes);
    }
    @Override
    @Transactional(readOnly = true)
    public List<IncomeResponseDto> getLatestIncome() {

        log.info("Fetching latest income transactions.");

        UserEntity user = getCurrentUser();

        List<IncomeEntity> incomes =
                incomeRepository.findTop10ByUserOrderByIncomeDateDesc(user);

        log.info(
                "Latest income records found: {}",
                incomes.size()
        );
        return IncomeMapper.toResponseDtoList(incomes);
    }

}
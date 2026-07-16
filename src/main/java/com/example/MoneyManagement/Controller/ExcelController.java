

package com.example.MoneyManagement.Controller;

import com.example.MoneyManagement.Entity.ExpenseEntity;
import com.example.MoneyManagement.Entity.IncomeEntity;
import com.example.MoneyManagement.Entity.UserEntity;
import com.example.MoneyManagement.Repository.ExpenseRepository;
import com.example.MoneyManagement.Repository.IncomeRepository;
import com.example.MoneyManagement.Repository.UserRepository;
import com.example.MoneyManagement.Service.ExcelExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayInputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelExportService excelExportService;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @GetMapping("/income")
    public ResponseEntity<Resource> downloadIncomeExcel(Authentication authentication) {

        System.out.println("====================================");
        System.out.println("Excel Download API Called");
        System.out.println("Authenticated User : " + authentication.getName());
        System.out.println("====================================");

        UserEntity user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        log.info("=====================================");
        log.info("LOGGED-IN USER");
        log.info("User ID    : {}", user.getId());
        log.info("User Email : {}", user.getEmail());
        log.info("=====================================");

        List<IncomeEntity> incomes =
                incomeRepository.findAllIncomeForExcel(user);

        log.info("=====================================");
        log.info("TOTAL INCOME RECORDS : {}", incomes.size());
        log.info("=====================================");

        for (IncomeEntity income : incomes) {

            log.info("-------------------------------------");
            log.info("Income ID        : {}", income.getId());
            log.info("Amount           : {}", income.getAmount());

            if (income.getUser() != null) {
                log.info("Income User ID   : {}", income.getUser().getId());
                log.info("Income User Email: {}", income.getUser().getEmail());
            } else {
                log.info("Income User      : NULL");
            }

            if (income.getCategory() != null) {
                log.info("Category         : {}", income.getCategory().getName());
            }
            log.info("-------------------------------------");
        }
        ByteArrayInputStream excelFile =
                excelExportService.exportIncomeToExcel(incomes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=income-report.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(excelFile));
    }

    @GetMapping("/expense")
    public ResponseEntity<Resource> downloadExpenseExcel(Authentication authentication) {

        UserEntity user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ExpenseEntity> expenses = expenseRepository.findByUser(user);

        ByteArrayInputStream excelFile =
                excelExportService.exportExpenseToExcel(expenses);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=expense-report.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(excelFile));
    }

}
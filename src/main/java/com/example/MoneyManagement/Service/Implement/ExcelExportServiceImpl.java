package com.example.MoneyManagement.Service.Implement;

import com.example.MoneyManagement.Entity.ExpenseEntity;
import com.example.MoneyManagement.Entity.IncomeEntity;
import com.example.MoneyManagement.Service.ExcelExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExcelExportServiceImpl implements ExcelExportService {

    private static final String[] INCOME_HEADERS = {
            "ID", "Amount", "Description", "Income Date", "Category", "Created At"
    };

    private static final String[] EXPENSE_HEADERS = {
            "ID", "Amount", "Description", "Expense Date", "Category", "Created At"
    };

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public ByteArrayInputStream exportIncomeToExcel(List<IncomeEntity> incomes) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Income Report");
            createHeaderRow(workbook, sheet, INCOME_HEADERS);

            int rowIdx = 1;
            for (IncomeEntity income : incomes) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(income.getId());
                row.createCell(1).setCellValue(income.getAmount().doubleValue());
                row.createCell(2).setCellValue(income.getDescription());
                row.createCell(3).setCellValue(
                        income.getIncomeDate() != null
                                ? income.getIncomeDate().format(DATE_FORMATTER)
                                : ""
                );
                row.createCell(4).setCellValue(
                        income.getCategory() != null
                                ? income.getCategory().getName()
                                : ""
                );
                row.createCell(5).setCellValue(
                        income.getCreatedAt() != null
                                ? income.getCreatedAt().format(DATE_TIME_FORMATTER)
                                : ""
                );
            }

            autoSizeColumns(sheet, INCOME_HEADERS.length);
            workbook.write(out);
            log.info("Income Excel export completed successfully");
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            log.error("Error exporting income to Excel", e);
            throw new RuntimeException("Failed to export income Excel file");
        }
    }

    @Override
    public ByteArrayInputStream exportExpenseToExcel(List<ExpenseEntity> expenses) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Expense Report");

            createHeaderRow(workbook, sheet, EXPENSE_HEADERS);

            int rowIdx = 1;
            for (ExpenseEntity expense : expenses) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(expense.getId());
                row.createCell(1).setCellValue(expense.getAmount().doubleValue());
                row.createCell(2).setCellValue(expense.getDescription());
                row.createCell(3).setCellValue(
                        expense.getExpenseDate() != null
                                ? expense.getExpenseDate().format(DATE_FORMATTER)
                                : ""
                );
                row.createCell(4).setCellValue(
                        expense.getCategory() != null
                                ? expense.getCategory().getName()
                                : ""
                );
                row.createCell(5).setCellValue(
                        expense.getCreatedAt() != null
                                ? expense.getCreatedAt().format(DATE_TIME_FORMATTER)
                                : ""
                );
            }

            autoSizeColumns(sheet, EXPENSE_HEADERS.length);

            workbook.write(out);

            log.info("Expense Excel export completed successfully");

            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            log.error("Error exporting expense to Excel", e);
            throw new RuntimeException("Failed to export expense Excel file");
        }
    }

    @Override
    public byte[] exportToExcel() {
        return new byte[0];
    }


    private void createHeaderRow(Workbook workbook, Sheet sheet, String[] headers) {

        Row headerRow = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setBold(true);
        style.setFont(font);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }
    private void autoSizeColumns(Sheet sheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}
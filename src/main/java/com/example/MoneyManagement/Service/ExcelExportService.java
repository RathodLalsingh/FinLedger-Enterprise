package com.example.MoneyManagement.Service;

import com.example.MoneyManagement.Entity.ExpenseEntity;
import com.example.MoneyManagement.Entity.IncomeEntity;
import java.io.ByteArrayInputStream;
import java.util.List;


public interface ExcelExportService {

    ByteArrayInputStream exportIncomeToExcel(List<IncomeEntity> incomes);
    ByteArrayInputStream exportExpenseToExcel(List<ExpenseEntity> expenses);
    byte[] exportToExcel();
}
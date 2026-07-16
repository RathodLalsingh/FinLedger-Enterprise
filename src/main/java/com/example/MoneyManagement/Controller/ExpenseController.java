package com.example.MoneyManagement.Controller;

import com.example.MoneyManagement.Dtos.ExpenseRequestDto;
import com.example.MoneyManagement.Dtos.ExpenseResponseDto;
import com.example.MoneyManagement.Service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponseDto> addExpense(@Valid @RequestBody ExpenseRequestDto requestDto){
        ExpenseResponseDto responseDto = expenseService.addExpense(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpenses(){
        List<ExpenseResponseDto> responseDto = expenseService.getAllExpenses();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(@PathVariable Long id){
        ExpenseResponseDto responseDto = expenseService.getExpenseById(id);
        return ResponseEntity.ok(responseDto);
    }

   @PutMapping("/{id}")
   public ResponseEntity<ExpenseResponseDto> updateExpense(@PathVariable Long id,@Valid @RequestBody ExpenseRequestDto requestDto){
        ExpenseResponseDto responseDto = expenseService.updateExpense(id,requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id){
        expenseService .deleteExpense(id);
        return ResponseEntity.ok("deleted expense successful");
    }
}

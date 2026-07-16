package com.example.MoneyManagement.Controller;

import com.example.MoneyManagement.Dtos.IncomeRequestDto;
import com.example.MoneyManagement.Dtos.IncomeResponseDto;
import com.example.MoneyManagement.Service.IncomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<IncomeResponseDto> addIncome(@Valid @RequestBody IncomeRequestDto requestDto){
        IncomeResponseDto responseDto = incomeService.addIncome(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<IncomeResponseDto>> getAllIncome(){
        List<IncomeResponseDto> responseDto = incomeService.getAllIncome();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeResponseDto> getIncomeById(@PathVariable Long id){
        IncomeResponseDto responseDto = incomeService.getIncomeById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeResponseDto> updateIncome(@PathVariable Long id,@Valid @RequestBody IncomeRequestDto requestDto){
        IncomeResponseDto responseDto = incomeService.updateIncome(id,requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIncome(@PathVariable Long id){
        incomeService.deleteIncome(id);
        return ResponseEntity.ok("deleted income sucessfully");

    }
}

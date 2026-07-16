package com.example.MoneyManagement.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeResponseDto {

    private Long id;
    private BigDecimal amount;
    private String description;

    //income date
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate incomeDate;

    private Long categoryId;
    private String categoryName;

}

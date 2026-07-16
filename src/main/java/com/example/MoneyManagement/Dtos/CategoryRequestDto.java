package com.example.MoneyManagement.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {

    @NotBlank(message = "category name is required")
    private String name;

    @NotBlank(message = "category type is required")
    @Pattern(
            regexp = "^(INCOME|EXPENSE)$",
            message = "Type must be either INCOME or EXPENSE"
    )
    private String type;
}

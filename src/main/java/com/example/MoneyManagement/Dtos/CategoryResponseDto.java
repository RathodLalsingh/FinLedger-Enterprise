package com.example.MoneyManagement.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CategoryResponseDto {

    private Long id;
    private String name;
    //category type income or expense
    private String type;
}

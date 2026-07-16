package com.example.MoneyManagement.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadResponseDto {

    private String fileName;
    private String imageUrl;
    private String message;
}

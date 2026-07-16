package com.example.MoneyManagement.Service;

import com.example.MoneyManagement.Dtos.CategoryRequestDto;
import com.example.MoneyManagement.Dtos.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto addCategory(CategoryRequestDto request);
    List<CategoryResponseDto> getAllCategories();
    CategoryResponseDto getCategoryById(Long id);
    CategoryResponseDto updateCategory(Long id, CategoryRequestDto request);
    CategoryResponseDto deleteCategory(Long id);

}
package com.example.MoneyManagement.Service.Implement;

import com.example.MoneyManagement.Dtos.CategoryRequestDto;
import com.example.MoneyManagement.Dtos.CategoryResponseDto;
import com.example.MoneyManagement.Entity.CategoryEntity;
import com.example.MoneyManagement.Entity.UserEntity;
import com.example.MoneyManagement.Exception.DuplicateResourceException;
import com.example.MoneyManagement.Exception.ResourceNotFoundException;
import com.example.MoneyManagement.Mapper.CategoryMapper;
import com.example.MoneyManagement.Repository.CategoryRepository;
import com.example.MoneyManagement.Repository.UserRepository;
import com.example.MoneyManagement.Service.CategoryService;
import com.example.MoneyManagement.Util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private UserEntity getCurrentUser() {
        String email = SecurityUtil.getCurrentUserEmail();
        if (email == null || email.isBlank()) {
            throw new ResourceNotFoundException("Authenticated user not found.");
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + email));
    }
    @Override
    public CategoryResponseDto addCategory(CategoryRequestDto request) {
        log.info("Creating category: {}", request.getName());
        UserEntity user = getCurrentUser();
        CategoryEntity existingCategory =
                categoryRepository.findByNameAndUser(
                        request.getName(),
                        user
                );

        if (existingCategory != null) {
            throw new DuplicateResourceException(
                    "Category already exists: " + request.getName()
            );
        }

        CategoryEntity category = CategoryMapper.toEntity(request);
        category.setUser(user);
        CategoryEntity savedCategory = categoryRepository.save(category);
        log.info("Category created successfully.");
        return CategoryMapper.toResponseDto(savedCategory);
    }
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories() {
        log.info("Fetching all categories.");
        UserEntity user = getCurrentUser();
        List<CategoryEntity> categories =
                categoryRepository.findByUser(user);
        return CategoryMapper.toResponseDtoList(categories);
    }
    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryById(Long id) {
        log.info("Fetching category with id: {}", id);

        UserEntity user = getCurrentUser();
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Category not found.");
        }
        return CategoryMapper.toResponseDto(category);
    }
    @Override
    public CategoryResponseDto updateCategory(
            Long id,
            CategoryRequestDto request) {
        log.info("Updating category with id: {}", id);
        UserEntity user = getCurrentUser();

        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Category not found.");
        }

        if (!category.getName().equalsIgnoreCase(request.getName())) {
            CategoryEntity duplicate =
                    categoryRepository.findByNameAndUser(
                            request.getName(),
                            user
                    );

            if (duplicate != null) {
                throw new DuplicateResourceException(
                        "Category already exists: " + request.getName()
                );
            }
        }

        CategoryMapper.updateEntity(category, request);
        CategoryEntity updatedCategory =
                categoryRepository.save(category);
        log.info("Category updated successfully.");

        return CategoryMapper.toResponseDto(updatedCategory);
    }
    @Override
    public CategoryResponseDto deleteCategory(Long id) {
        log.info("Deleting category with id: {}", id);
        UserEntity user = getCurrentUser();

        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Category not found.");
        }
        CategoryResponseDto response =
                CategoryMapper.toResponseDto(category);
        categoryRepository.delete(category);
        log.info("Category deleted successfully.");
        return response;
    }
}
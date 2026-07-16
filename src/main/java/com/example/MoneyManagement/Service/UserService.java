package com.example.MoneyManagement.Service;

import com.example.MoneyManagement.Dtos.UpdateUserRequestDto;
import com.example.MoneyManagement.Dtos.UserResponseDto;
import org.springframework.security.core.userdetails.User;

public interface UserService {

    UserResponseDto getUserById(Long id);
    UserResponseDto  updateUser(Long id, UpdateUserRequestDto requestDto);
    void deleteUser(Long id);
}

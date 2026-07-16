package com.example.MoneyManagement.Service;

import com.example.MoneyManagement.Dtos.AuthResponseDto;
import com.example.MoneyManagement.Dtos.LoginRequestDto;
import com.example.MoneyManagement.Dtos.SignupRequestDto;

public interface AuthService {

    AuthResponseDto registerUser(SignupRequestDto signupRequestDto);
        AuthResponseDto loginUser(LoginRequestDto login);

}

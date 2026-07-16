package com.example.MoneyManagement.Controller;

import com.example.MoneyManagement.Dtos.AuthResponseDto;
import com.example.MoneyManagement.Dtos.LoginRequestDto;
import com.example.MoneyManagement.Dtos.SignupRequestDto;
import com.example.MoneyManagement.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerUser(@Valid @RequestBody SignupRequestDto signupRequestDto){

        AuthResponseDto responseDto = authService.registerUser(signupRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@Valid @RequestBody LoginRequestDto requestDto){
        AuthResponseDto respsonse = authService.loginUser(requestDto);
        return ResponseEntity.ok(respsonse);
    }

}

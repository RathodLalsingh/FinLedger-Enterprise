package com.example.MoneyManagement.Service.Implement;

import com.example.MoneyManagement.Dtos.AuthResponseDto;
import com.example.MoneyManagement.Dtos.LoginRequestDto;
import com.example.MoneyManagement.Dtos.SignupRequestDto;
import com.example.MoneyManagement.Entity.UserEntity;
import com.example.MoneyManagement.Exception.DuplicateResourceException;
import com.example.MoneyManagement.Exception.InvalidCredentialsException;
import com.example.MoneyManagement.Mapper.UserMapper;
import com.example.MoneyManagement.Repository.UserRepository;
import com.example.MoneyManagement.Security.JwtUtil;
import com.example.MoneyManagement.Service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponseDto registerUser(SignupRequestDto signupRequestDto) {
        log.info("Register request received for email: {}", signupRequestDto.getEmail());

        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new DuplicateResourceException(
                    "User already exists with email: " + signupRequestDto.getEmail()
            );
        }
        UserEntity user = UserMapper.toEntity(signupRequestDto);
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        user.setRole("ROLE_USER");

        UserEntity savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser.getEmail());
        log.info("User registered successfully: {}", savedUser.getEmail());
        return UserMapper.toAuthResponseDto(savedUser, token);
    }
    @Override
    public AuthResponseDto loginUser(LoginRequestDto loginRequestDto) {
        log.info("Login request received for email: {}", loginRequestDto.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            log.error("Invalid login attempt for email: {}", loginRequestDto.getEmail());
            throw new InvalidCredentialsException("Invalid email or password");
        }
        UserEntity user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("User not found")
                );
        String token = jwtUtil.generateToken(user.getEmail());
        log.info("User logged in successfully: {}", user.getEmail());
        return UserMapper.toAuthResponseDto(user, token);
    }
}
package com.example.MoneyManagement.Mapper;

import com.example.MoneyManagement.Dtos.SignupRequestDto;
import com.example.MoneyManagement.Dtos.UpdateUserRequestDto;
import com.example.MoneyManagement.Dtos.UserResponseDto;
import com.example.MoneyManagement.Dtos.AuthResponseDto;
import com.example.MoneyManagement.Entity.UserEntity;
import lombok.experimental.UtilityClass;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class UserMapper {
    public static UserEntity toEntity(SignupRequestDto request) {

        if (request == null) {
            return null;
        }
        return UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
    public static UserResponseDto toResponseDto(UserEntity user) {

        if (user == null) {
            return null;
        }
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .role(user.getRole())
                .build();
    }
    public static AuthResponseDto toAuthResponseDto(
            UserEntity user,
            String token) {

        if (user == null) {
            return null;
        }
        return AuthResponseDto.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
    public static void updateEntity(
            UserEntity entity,
            UpdateUserRequestDto request) {

        if (entity == null || request == null) {
            return;
        }
        if (request.getName() != null &&
                !request.getName().trim().isEmpty()) {
            entity.setName(request.getName().trim());
        }
        if (request.getEmail() != null &&
                !request.getEmail().trim().isEmpty()) {

            entity.setEmail(request.getEmail().trim());
        }

        if (request.getProfileImage() != null &&
                !request.getProfileImage().trim().isEmpty()) {

            entity.setProfileImage(
                    request.getProfileImage().trim()
            );
        }
    }
    public static List<UserResponseDto> toResponseDtoList(
            List<UserEntity> users) {
        List<UserResponseDto> responseList = new ArrayList<>();
        if (users == null || users.isEmpty()) {
            return responseList;
        }
        for (UserEntity user : users) {
            responseList.add(toResponseDto(user));
        }
        return responseList;
    }
    public static boolean isValid(UserEntity user) {
        return user != null;
    }
    public static boolean isEmpty(List<UserEntity> users) {
        return users == null || users.isEmpty();
    }
    public static UserResponseDto emptyResponse() {

        return UserResponseDto.builder()
                .id(null)
                .name("")
                .email("")
                .profileImage("")
                .role("")
                .build();
    }


    public static UserEntity copy(UserEntity user) {

        if (user == null) {
            return null;
        }
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .profileImage(user.getProfileImage())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
package com.example.MoneyManagement.Service.Implement;

import com.example.MoneyManagement.Dtos.UpdateUserRequestDto;
import com.example.MoneyManagement.Dtos.UserResponseDto;
import com.example.MoneyManagement.Entity.UserEntity;
import com.example.MoneyManagement.Exception.DuplicateResourceException;
import com.example.MoneyManagement.Exception.ResourceNotFoundException;
import com.example.MoneyManagement.Mapper.UserMapper;
import com.example.MoneyManagement.Repository.UserRepository;
import com.example.MoneyManagement.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {

        log.info("Fetching user with id: {}", id);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id : " + id));

        log.info("User fetched successfully.");

        return UserMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto updateUser(Long id,
                                      UpdateUserRequestDto request) {

        log.info("Updating user with id: {}", id);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id : " + id));

        if (request.getEmail() != null
                && !request.getEmail().equalsIgnoreCase(user.getEmail())) {

            if (userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException(
                        "Email already exists : " + request.getEmail());
            }
        }
        UserMapper.updateEntity(user, request);

        UserEntity updatedUser = userRepository.save(user);

        log.info("User updated successfully.");

        return UserMapper.toResponseDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        log.info("Deleting user with id: {}", id);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id : " + id));

        userRepository.delete(user);

        log.info("User deleted successfully.");
    }

}
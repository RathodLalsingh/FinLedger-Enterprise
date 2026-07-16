package com.example.MoneyManagement.Security;

import com.example.MoneyManagement.Entity.UserEntity;
import com.example.MoneyManagement.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user=userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with email: "+email));
        return new CustomUserDetails(user);
    }
}

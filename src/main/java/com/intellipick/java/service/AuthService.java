package com.intellipick.java.service;

import com.intellipick.java.dto.mapper.AuthMapper;
import com.intellipick.java.dto.request.SignupRequestDto;
import com.intellipick.java.dto.response.SignupResponseDto;
import com.intellipick.java.entity.User;
import com.intellipick.java.entity.UserRole;
import com.intellipick.java.repository.UserRepository;
import com.intellipick.java.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final AuthMapper authMapper;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        validDuplicateUserName(signupRequestDto.username());

        User user = User.create(signupRequestDto.withPassword(passwordEncoder.encode(signupRequestDto.password())));
        UserRole userRole = UserRole.create(user);

        userRepository.save(user);
        userRoleRepository.save(userRole);

        return authMapper.convertToSignupResponseDto(user, List.of(userRole.getAuthorityName()));
    }

    private void validDuplicateUserName(String userName){
        if(userRepository.findByUsername(userName).isPresent()){
            throw new IllegalArgumentException("이미 등록된 이름입니다.");
        }
    }
}

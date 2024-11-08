package com.intellipick.java.service;

import com.intellipick.java.dto.mapper.AuthMapper;
import com.intellipick.java.dto.request.SignRequestDto;
import com.intellipick.java.dto.request.SignupRequestDto;
import com.intellipick.java.dto.response.SignResponseDto;
import com.intellipick.java.dto.response.SignupResponseDto;
import com.intellipick.java.entity.User;
import com.intellipick.java.entity.UserRole;
import com.intellipick.java.jwt.JwtUtil;
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

    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        validDuplicateUserName(signupRequestDto.username());

        User user = User.create(signupRequestDto.withPassword(passwordEncoder.encode(signupRequestDto.password())));
        UserRole userRole = UserRole.create(user);

        userRepository.save(user);
        userRoleRepository.save(userRole);

        return authMapper.convertToSignupResponseDto(user, List.of(userRole.getAuthorityName()));
    }

    @Transactional
    public SignResponseDto sign(SignRequestDto signRequestDto) {
        User user = existUserByUsername(signRequestDto.username());

        if (!passwordEncoder.matches(signRequestDto.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.createAccessToken(user.getUsername());
        String refreshToken = jwtUtil.createRefreshToken(user.getUsername());

        return authMapper.convertToSignResponseDto(accessToken,refreshToken);
    }

    private void validDuplicateUserName(String username){
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("이미 등록된 이름입니다.");
        }
    }

    private User existUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(
            () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );
    }
}

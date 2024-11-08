package com.intellipick.java.dto.mapper;

import com.intellipick.java.dto.response.SignupResponseDto;
import com.intellipick.java.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthMapper {
    public SignupResponseDto convertToSignupResponseDto(User user, List<String> authorityNameList) {
        return new SignupResponseDto(
            user.getUsername(),
            user.getNickname(),
            authorityNameList
        );
    }
}

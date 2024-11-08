package com.intellipick.java.dto.response;

import com.intellipick.java.entity.UserRole;

import java.util.List;

public record SignupResponseDto (
    String username,
    String nickname,
    List<String> authorities
) {
}

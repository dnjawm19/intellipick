package com.intellipick.java.dto.response;

public record SignResponseDto (
    String accessToken,
    String refreshToken
) {
}

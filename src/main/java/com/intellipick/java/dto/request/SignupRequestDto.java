package com.intellipick.java.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.With;

public record SignupRequestDto(
    @NotBlank
    String username,
    @With
    @NotBlank
    String password,
    @NotBlank
    String nickname
) {
}

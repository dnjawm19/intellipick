package com.intellipick.java.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignRequestDto (
    @NotBlank
    String username,
    @NotBlank
    String password
) {
}

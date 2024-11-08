package com.intellipick.java.controller;

import com.intellipick.java.dto.request.SignRequestDto;
import com.intellipick.java.dto.request.SignupRequestDto;
import com.intellipick.java.dto.response.SignResponseDto;
import com.intellipick.java.dto.response.SignupResponseDto;
import com.intellipick.java.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return authService.signup(signupRequestDto);
    }

    @PostMapping("/sign")
    public SignResponseDto sign(@Valid @RequestBody SignRequestDto signRequestDto) {
        return authService.sign(signRequestDto);
    }
}

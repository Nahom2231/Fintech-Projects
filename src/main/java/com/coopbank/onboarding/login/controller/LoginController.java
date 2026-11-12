package com.coopbank.onboarding.login.controller;

import com.coopbank.onboarding.login.dto.LoginRequest;
import com.coopbank.onboarding.login.dto.LoginResponse;
import com.coopbank.onboarding.login.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return loginService.login(request);
    }
} 

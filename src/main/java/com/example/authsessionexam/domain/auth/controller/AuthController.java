package com.example.authsessionexam.domain.auth.controller;

import com.example.authsessionexam.domain.auth.controller.dto.SignupRequest;
import com.example.authsessionexam.domain.auth.service.AuthApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthApplicationService service;

    /**
     * 회원가입 API
     */
    @RequestMapping("/signup")
    public void signup(@Valid SignupRequest request) {
        this.service.signup(request);
    }



}
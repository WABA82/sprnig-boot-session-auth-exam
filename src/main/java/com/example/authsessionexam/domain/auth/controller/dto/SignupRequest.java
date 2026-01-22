package com.example.authsessionexam.domain.auth.controller.dto;

import com.example.authsessionexam.global.enums.Gender;
import com.example.authsessionexam.global.validation.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupRequest(
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String name,
        @NotBlank
        String phone,
        @NotNull
        @ValidEnum(enumClass = Gender.class, ignoreCase = true)
        Gender gender
) {
}
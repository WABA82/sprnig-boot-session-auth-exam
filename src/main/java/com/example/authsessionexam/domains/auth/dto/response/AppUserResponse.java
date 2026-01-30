package com.example.authsessionexam.domains.auth.dto.response;


import com.example.authsessionexam.domains.auth.model.AppUser;

public record AppUserResponse(
        String email,
        String nickname
) {

    public static AppUserResponse from(AppUser user) {
        return new AppUserResponse(
                user.getEmail(),
                user.getNickname()
        );
    }

}

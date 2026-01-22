package com.example.authsessionexam.domain.auth.exception;

import com.example.authsessionexam.global.exception.DomainErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;


@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum AuthErrorCode implements DomainErrorCode {

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "NOT_FOUND_USER", "해당 사용자를 찾을 수 없습니다.");

    HttpStatus httpStatus;
    String errorCode;
    String errorMessage;

    AuthErrorCode(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}

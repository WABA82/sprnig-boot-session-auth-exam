package com.example.authsessionexam.domains.auth.exception;

import com.example.authsessionexam.global.exception.BusinessException;
import com.example.authsessionexam.global.exception.ErrorCode;

public class AuthException extends BusinessException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}

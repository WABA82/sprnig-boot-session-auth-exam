package com.example.sessionauth.domains.auth.exception;

import com.example.sessionauth.global.exception.BusinessException;
import com.example.sessionauth.global.exception.ErrorCode;

public class AuthException extends BusinessException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}

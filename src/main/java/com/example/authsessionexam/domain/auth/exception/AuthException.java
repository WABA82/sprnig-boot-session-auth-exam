package com.example.authsessionexam.domain.auth.exception;

import com.example.authsessionexam.global.exception.DomainErrorCode;
import com.example.authsessionexam.global.exception.DomainException;

public class AuthException extends DomainException {

    public AuthException(DomainErrorCode domainErrorCode) {
        super(domainErrorCode);
    }

}

package com.example.authsessionexam.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DomainException extends RuntimeException {

    HttpStatus httpStatus;
    String code;
    String message;

    public DomainException(DomainErrorCode domainErrorCode) {
        super(domainErrorCode.getErrorMessage());

        this.httpStatus = domainErrorCode.getHttpStatus();
        this.code = domainErrorCode.getErrorCode();
        this.message = domainErrorCode.getErrorMessage();
    }

}

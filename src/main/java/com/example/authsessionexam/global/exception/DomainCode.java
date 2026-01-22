package com.example.authsessionexam.global.exception;

import org.springframework.http.HttpStatus;

public interface DomainCode {

    String getCode();

    HttpStatus getHttpStatus();

    String getDefaultMessage();

}

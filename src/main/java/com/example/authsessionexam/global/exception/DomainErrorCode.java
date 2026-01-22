package com.example.authsessionexam.global.exception;

public interface DomainErrorCode extends DomainCode {

    String getErrorCode();

    String getErrorMessage();

    @Override
    default String getCode() {
        return this.getErrorCode();
    }

    @Override
    default String getDefaultMessage() {
        return this.getErrorMessage();
    }

}

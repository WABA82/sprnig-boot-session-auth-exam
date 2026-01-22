package com.example.authsessionexam.global.exception;

import com.example.authsessionexam.global.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    private static final String SERVER_ERROR = "SERVER_ERROR";

    /**
     * 도메인
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiResponse<Void>> handleDomainException(DomainException ex) {
        log.warn("[DomainException] : code={}, message={}", ex.getCode(), ex.getMessage());
        return ApiResponse.fail(ex.getHttpStatus(), ex.getCode(), ex.getMessage());
    }

    /**
     * Request Body 바인딩 성공 후, 유효성 검사 실패 시
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = extractBindErrorMessages(ex);
        log.warn("[ValidationException] : {}", errorMessage);
        return ApiResponse.fail(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, errorMessage);
    }

    /**
     * ( @ModelAttribute ) 바인딩 실패 또는 유효성 검사 실패 시
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Void>> handleBindException(BindException ex) {
        String errorMessage = extractBindErrorMessages(ex);
        log.warn("[BindException] : {}", errorMessage);
        return ApiResponse.fail(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, errorMessage);
    }

    /**
     * 서버 에러
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("[Exception] : ", ex);
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, SERVER_ERROR, ex.getMessage());
    }

    /**
     * BindException 메시지 추출
     */
    private String extractBindErrorMessages(BindException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }
}
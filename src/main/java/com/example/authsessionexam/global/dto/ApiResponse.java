package com.example.authsessionexam.global.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    // API 요청 성공 여부 (true/false)
    boolean success;
    // API 응답 코드
    String code;
    // API 메시지
    String message;
    // 실제 응답 데이터 (성공 시에만 포함되며, 실패 시에는 JSON 제외)
    T data;

    /**
     * 200 OK: 요청이 성공적으로 처리되었으며, 요청한 데이터가 응답 본문에 포함되어 반환될 때 사용 (GET, PUT, POST)
     */
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(true)
                .code("ok")
                .message("요청이 성공적으로 처리되었습니다.")
                .data(data)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 204 No Content: 요청은 성공적으로 처리되었으나, 클라이언트에게 돌려줄 콘텐츠(데이터)가 없을 때 사용 (DELETE)
     */
    public static <T> ResponseEntity<ApiResponse<T>> noContent() {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(true)
                .code("no_content")
                .message("요청이 성공적으로 처리되었습니다 (반환 데이터 없음).")
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    /**
     * 201 Created: 리소스 생성 요청이 성공했을 때 사용 (POST)
     */
    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(true)
                .code("created")
                .message("데이터가 성공적으로 생성되었습니다.")
                .data(data)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 응답 실패
     */
    public static <T> ResponseEntity<ApiResponse<T>> fail(HttpStatus httpStatus, String errorCode, String errorMessage) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(false)
                .code(errorCode)
                .message(errorMessage)
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }

}
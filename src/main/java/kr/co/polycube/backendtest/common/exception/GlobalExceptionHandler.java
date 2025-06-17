package kr.co.polycube.backendtest.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

// 전역 예외를 처리하는 핸들러
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 존재하지 않는 API를 호출했을 때 발생하는 예외를 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoHandlerFound(NoHandlerFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("reason", "존재하지 않는 API입니다.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 비즈니스 로직에서 발생한 커스텀 예외(CommonException)를 처리
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException e) {
        return ResponseEntity.status(e.getCode()).body(new ErrorResponse(e));
    }

}

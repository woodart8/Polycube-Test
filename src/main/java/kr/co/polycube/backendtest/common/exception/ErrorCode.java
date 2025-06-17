package kr.co.polycube.backendtest.common.exception;

import lombok.Getter;

// 비즈니스 로직에서 발생할 수 있는 에러의 코드와 메시지를 정의한 열거형
@Getter
public enum ErrorCode {

    INVALID_USER_NAME(400, "유효하지 않은 회원 이름입니다."),
    NOT_FOUND_USER(404, "회원이 존재하지 않습니다.");

    private final int code;
    private final String reason;

    ErrorCode(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

}

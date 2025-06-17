package kr.co.polycube.backendtest.common.exception;

import lombok.Getter;

// 비즈니스 로직에서 발생하는 예외를 처리하는 커스텀 런타임 예외 클래스
@Getter
public class CommonException extends RuntimeException {

    private final ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getReason());
        this.errorCode = errorCode;
    }

    public int getCode() {
        return errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return errorCode.getReason();
    }

}

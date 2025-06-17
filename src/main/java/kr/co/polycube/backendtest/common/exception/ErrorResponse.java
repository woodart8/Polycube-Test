package kr.co.polycube.backendtest.common.exception;

import lombok.Getter;

// 클라이언트에게 전달할 에러 메시지를 담는 응답 DTO
@Getter
public class ErrorResponse {

    private final String reason;

    public ErrorResponse(CommonException e) {
        this.reason = e.getMessage();
    }

}
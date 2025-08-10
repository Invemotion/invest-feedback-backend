package org.invemotion.global.dto.enums;

import org.springframework.http.HttpStatus;

public enum SuccessCode {
    OK(HttpStatus.OK, 200, "요청이 성공적으로 처리되었습니다."),
    CREATED(HttpStatus.CREATED, 201, "성공적으로 생성되었습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    SuccessCode(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() { return httpStatus; }
    public int getCode() { return code; }
    public String getMessage() { return message; }
}

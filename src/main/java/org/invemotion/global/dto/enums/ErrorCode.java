package org.invemotion.global.dto.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // Trade
    MISSING_PERIOD             (HttpStatus.BAD_REQUEST, 40005, "조회 기간(start/end)이 누락되었습니다."),
    INVALID_PERIOD_RANGE       (HttpStatus.BAD_REQUEST, 40006, "start는 end보다 이전이어야 합니다."),


    // Bond_Purchase
    PURCHASE_NOT_FOUND (HttpStatus.NOT_FOUND, 40010, "해당 매입 거래를 찾을 수 없습니다."),
    PURCHASE_ALREADY_CANCELED (HttpStatus.CONFLICT, 40011, "이미 취소된 거래입니다."),


    // 공용
    INVALID_REQUEST (HttpStatus.BAD_REQUEST, 40000, "요청 필드가 잘못되었습니다."),
    MISSING_USER_ID_HEADER     (HttpStatus.BAD_REQUEST, 40001, "X-User-Id 헤더가 누락되었습니다."),
    INVALID_TAG (HttpStatus.NOT_FOUND, 40400, "해당 태그가 존재하지 않습니다."),
    CONCURRENT_UPDATE (HttpStatus.PRECONDITION_FAILED,     41200, "수정하려는 정보가 최신 상태가 아닙니다."),
    IF_MATCH_REQUIRED (HttpStatus.PRECONDITION_REQUIRED,   42800, "If-Match 헤더(버전 정보)가 누락되었습니다."),
    INTERNAL_ERROR (HttpStatus.INTERNAL_SERVER_ERROR, 50000, "서버 내부 오류입니다.");


    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() { return httpStatus; }
    public int getCode() { return code; }
    public String getMessage() { return message; }
}




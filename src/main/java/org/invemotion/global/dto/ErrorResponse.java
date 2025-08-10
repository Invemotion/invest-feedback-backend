package org.invemotion.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.invemotion.global.dto.enums.ErrorCode;

@Schema(description = "오류 응답")
public record ErrorResponse(
        @Schema(description = "에러 코드", example = "40000") int status,
        @Schema(description = "에러 메시지", example = "요청 필드가 잘못되었습니다.") String message
) {
    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code.getCode(), code.getMessage());
    }
}
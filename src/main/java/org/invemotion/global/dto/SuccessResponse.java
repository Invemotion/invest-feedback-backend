package org.invemotion.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.invemotion.global.dto.enums.SuccessCode;

@Schema(description = "성공 응답")
public record SuccessResponse<T>(
        @Schema(description = "업무 상태 코드", example = "20000")
        int status,
        @Schema(description = "성공 메시지", example = "요청이 성공적으로 처리되었습니다.")
        String message,
        @Schema(description = "실제 데이터") T data
) {
    public static <T> SuccessResponse<T> of(SuccessCode code, T data) {
        return new SuccessResponse<>(code.getCode(), code.getMessage(), data);
    }

    public static SuccessResponse<Void> of(SuccessCode code) {
        return new SuccessResponse<>(code.getCode(), code.getMessage(), null);
    }
}
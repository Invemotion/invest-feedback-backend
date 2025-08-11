package org.invemotion.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import org.invemotion.domain.report.enums.PeriodType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReportResponse(
        Long id,
        Long userId,
        PeriodType periodType,
        LocalDate reportDate,
        String content,
        JsonNode meta,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

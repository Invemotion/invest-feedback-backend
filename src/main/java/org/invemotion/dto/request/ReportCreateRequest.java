package org.invemotion.dto.request;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.invemotion.domain.report.enums.PeriodType;

import java.time.LocalDate;

public record ReportCreateRequest(
        @NotNull PeriodType periodType,
        @NotNull LocalDate reportDate,
        @NotBlank String content,
        JsonNode meta
) {
}

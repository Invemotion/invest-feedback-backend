package org.invemotion.dto.request;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;

public record ReportUpdateRequest(
        @NotBlank String content,
        JsonNode meta
) {
}

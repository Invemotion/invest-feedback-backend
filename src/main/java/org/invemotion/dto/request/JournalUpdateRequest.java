package org.invemotion.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.invemotion.domain.journal.enums.BehaviorType;
import org.invemotion.domain.journal.enums.Emotion;

public record JournalUpdateRequest(
        @NotBlank
        String reason,
        @NotNull
        Emotion emotion,
        @NotNull
        BehaviorType behavior
) {
}

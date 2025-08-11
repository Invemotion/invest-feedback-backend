package org.invemotion.dto.response;

import org.invemotion.domain.journal.enums.BehaviorType;
import org.invemotion.domain.journal.enums.Emotion;

public record JournalResponse(
        Long id,
        Long tradeId,
        Long userId,
        String reason,
        Emotion emotion,
        BehaviorType behavior
) {
}

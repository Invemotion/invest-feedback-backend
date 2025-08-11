package org.invemotion.dto.response;

import org.invemotion.domain.journal.enums.BehaviorType;
import org.invemotion.domain.journal.enums.Emotion;
import org.invemotion.domain.trade.enums.ResultType;

public record JournalCreateResponse(
        Long id
) {
}

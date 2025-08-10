// src/main/java/org/invemotion/service/validator/TradeQueryValidator.java
package org.invemotion.service.validator;

import org.invemotion.global.dto.enums.ErrorCode;
import org.invemotion.global.exception.CustomException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TradeQueryValidator {

    public void validateUser(Long userId) {
        if (userId == null) {
            throw new CustomException(ErrorCode.MISSING_USER_ID_HEADER);
        }
    }

    public void validatePeriod(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new CustomException(ErrorCode.MISSING_PERIOD);
        }
        if (!start.isBefore(end)) {
            throw new CustomException(ErrorCode.INVALID_PERIOD_RANGE);
        }
    }
}


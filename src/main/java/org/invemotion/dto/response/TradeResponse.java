package org.invemotion.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TradeResponse(
        Long id,
        UUID tradeId,
        String stockName,
        String stockCode,
        String actionType,
        String orderType,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime orderTime,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime completedTime,
        BigDecimal marketPriceAtOrder,
        BigDecimal pricePerBuy,
        BigDecimal pricePerSell,
        Integer quantity,
        BigDecimal totalAmount,
        String resultType,
        BigDecimal exchangeRate
) {
}

package org.invemotion.service;

import lombok.RequiredArgsConstructor;
import org.invemotion.domain.trade.Trade;
import org.invemotion.domain.trade.enums.ActionType;
import org.invemotion.domain.trade.enums.OrderType;
import org.invemotion.domain.trade.enums.ResultType;
import org.invemotion.dto.response.TradeResponse;
import org.invemotion.global.dto.PageResponse;
import org.invemotion.repository.TradeRepository;
import org.invemotion.service.validator.TradeQueryValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TradeQueryService {

    private final TradeRepository tradeRepository;
    private final TradeQueryValidator validator;

    public PageResponse<TradeResponse> getTradePage(
            Long userId, LocalDateTime start, LocalDateTime end, Pageable pageable
    ) {
        validator.validateUser(userId);
        validator.validatePeriod(start, end);

        Page<Trade> p = tradeRepository.findPageByUserAndCompletedBetween(userId, start, end, pageable);

        List<TradeResponse> items = p.getContent().stream()
                .map(this::toDto)
                .toList();

        return new PageResponse<>(items, p.getNumber(), p.getSize(), p.getTotalElements(), p.isLast());
    }

    private TradeResponse toDto(Trade t) {
        return new TradeResponse(
                t.getId(),
                t.getTradeUUID(),
                t.getSymbolName(),
                t.getSymbolCode(),
                label(t.getActionType()),
                label(t.getOrderType()),
                t.getOrderTime(),
                t.getCompletedTime(),
                t.getMarketPriceAtOrder(),
                t.getPricePerBuy(),
                t.getPricePerSell(),
                t.getQuantity(),
                t.getTotalAmount(),
                label(t.getResultType()),
                t.getExchangeRate()
        );
    }

    private String label(ActionType a) {
        return switch (a) {
            case BUY  -> "매수";
            case SELL -> "매도";
        };
    }

    private String label(OrderType o) {
        return switch (o) {
            case MARKET -> "시장가";
            case LIMIT  -> "지정가";
        };
    }

    private String label(ResultType r) {
        if (r == null) return null;
        return switch (r) {
            case BREAK_EVEN -> "본전";
            case PROFIT     -> "수익";
            case LOSS       -> "손실";
        };
    }
}

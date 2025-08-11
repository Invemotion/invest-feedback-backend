package org.invemotion.service;

import lombok.RequiredArgsConstructor;
import org.invemotion.domain.trade.Trade;
import org.invemotion.domain.trade.enums.ActionType;
import org.invemotion.domain.trade.enums.OrderType;
import org.invemotion.domain.trade.enums.ResultType;
import org.invemotion.dto.response.TradeResponse;
import org.invemotion.global.dto.PageResponse;
import org.invemotion.repository.TradeRepository;
import org.invemotion.repository.JournalRepository;
import org.invemotion.repository.JournalRepository.TradeJournalPair;
import org.invemotion.service.validator.TradeQueryValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TradeQueryService {

    private final TradeRepository tradeRepository;
    private final JournalRepository journalRepository;
    private final TradeQueryValidator validator;

    public PageResponse<TradeResponse> getTradePage(
            Long userId,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    ) {
        validator.validateUser(userId);
        validator.validatePeriod(start, end);

        Page<Trade> p = tradeRepository.findPageByUserAndCompletedBetween(userId, start, end, pageable);

        List<Trade> trades = p.getContent();
        List<Long> tradeIds = trades.stream().map(Trade::getId).toList();

        Map<Long, Long> tradeIdToJournalId = tradeIds.isEmpty()
                ? Collections.emptyMap()
                : journalRepository.findJournalIdsByTradeIds(tradeIds).stream()
                .collect(Collectors.toMap(TradeJournalPair::getTradeId, TradeJournalPair::getId));

        List<TradeResponse> items = trades.stream()
                .map(t -> {
                    Long journalId = tradeIdToJournalId.get(t.getId());
                    boolean hasJournal = (journalId != null);
                    return toDto(t, hasJournal, journalId);
                })
                .toList();

        return new PageResponse<>(items, p.getNumber(), p.getSize(), p.getTotalElements(), p.isLast());
    }

    private TradeResponse toDto(Trade t, boolean hasJournal, Long journalId) {
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
                t.getExchangeRate(),
                hasJournal,
                journalId
        );
    }

    private String label(ActionType a) {
        return switch (a) {
            case BUY -> "매수";
            case SELL -> "매도";
        };
    }
    private String label(OrderType o) {
        return switch (o) {
            case MARKET -> "시장가";
            case LIMIT -> "지정가";
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

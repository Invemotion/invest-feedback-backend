package org.invemotion.controller;

import lombok.RequiredArgsConstructor;
import org.invemotion.domain.trade.Trade;
import org.invemotion.dto.response.JournalResponse;
import org.invemotion.dto.response.TradeListData;
import org.invemotion.dto.response.TradeResponse;
import org.invemotion.global.dto.PageResponse;
import org.invemotion.global.dto.SuccessResponse;
import org.invemotion.global.dto.enums.SuccessCode;
import org.invemotion.service.TradeQueryService;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController{

    private final TradeQueryService tradeQueryService;

    @GetMapping
    public ResponseEntity<SuccessResponse<TradeListData>> list(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        LocalDateTime start = month.atDay(1).atStartOfDay();
        LocalDateTime end   = month.plusMonths(1).atDay(1).atStartOfDay();
        Pageable pageable   = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "completedTime"));

        PageResponse<TradeResponse> pageDto =
                tradeQueryService.getTradePage(userId, start, end, pageable);

        return ResponseEntity
                .status(SuccessCode.OK.getHttpStatus())
                .body(SuccessResponse.of(SuccessCode.OK, new TradeListData(pageDto)));
    }
}

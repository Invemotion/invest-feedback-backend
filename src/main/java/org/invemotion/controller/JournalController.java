package org.invemotion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.invemotion.domain.journal.Journal;
import org.invemotion.dto.request.JournalCreateRequest;
import org.invemotion.dto.request.JournalUpdateRequest;
import org.invemotion.dto.response.JournalCreateResponse;
import org.invemotion.dto.response.JournalListData;
import org.invemotion.dto.response.JournalResponse;
import org.invemotion.dto.response.TradeListData;
import org.invemotion.global.dto.SuccessResponse;
import org.invemotion.global.dto.enums.SuccessCode;
import org.invemotion.service.JournalCommandService;
import org.invemotion.service.JournalQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JournalController {

    private final JournalCommandService journalCommandService;
    private final JournalQueryService journalQueryService;

    @PostMapping("trades/{tradeId}/journal")
    public ResponseEntity<SuccessResponse<JournalResponse>> create(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long tradeId,
            @Valid @RequestBody JournalCreateRequest request
    ) {
        Journal journal = journalCommandService.create(userId, tradeId, request);
        JournalResponse body = journalQueryService.getById(userId, journal.getId());
        return ResponseEntity
                .status(SuccessCode.OK.getHttpStatus())
                .body(SuccessResponse.of(SuccessCode.OK, body));
    }

    @GetMapping("journals/{id}")
    public ResponseEntity<SuccessResponse<JournalResponse>> getJournal(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id
    ){
        JournalResponse journal = journalQueryService.getById(userId, id);

        return ResponseEntity
                .status(SuccessCode.OK.getHttpStatus())
                .body(SuccessResponse.of(SuccessCode.OK, journal));
    }

    @PutMapping("journals/{id}")
    public ResponseEntity<SuccessResponse<JournalResponse>> update(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @Valid @RequestBody JournalUpdateRequest request
    ) {
        journalCommandService.update(userId, id, request);
        JournalResponse body = journalQueryService.getById(userId, id);
        return ResponseEntity
                .status(SuccessCode.OK.getHttpStatus())
                .body(SuccessResponse.of(SuccessCode.OK, body));
    }
}

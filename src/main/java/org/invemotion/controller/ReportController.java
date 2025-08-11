package org.invemotion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.invemotion.domain.report.Report;
import org.invemotion.dto.request.ReportCreateRequest;
import org.invemotion.dto.request.ReportUpdateRequest;
import org.invemotion.dto.response.ReportListData;
import org.invemotion.dto.response.ReportResponse;
import org.invemotion.global.dto.PageResponse;
import org.invemotion.global.dto.SuccessResponse;
import org.invemotion.global.dto.enums.SuccessCode;
import org.invemotion.service.ReportCommandService;
import org.invemotion.service.ReportQueryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportController {

    private final ReportCommandService reportCommandService;
    private final ReportQueryService reportQueryService;

    @PostMapping("report")
    public ResponseEntity<SuccessResponse<ReportResponse>> create(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody ReportCreateRequest request
    ) {
        Report report = reportCommandService.create(userId, request);
        ReportResponse body = reportQueryService.getById(report.getId());

        return ResponseEntity
                .status(SuccessCode.CREATED.getHttpStatus())
                .body(SuccessResponse.of(SuccessCode.CREATED, body));
    }

    @PatchMapping("reports/{id}")
    public ResponseEntity<SuccessResponse<ReportResponse>> update(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @Valid @RequestBody ReportUpdateRequest request
            ) {
        reportCommandService.update(userId, id, request);
        ReportResponse body = reportQueryService.getById(id);
        return ResponseEntity
                .status(SuccessCode.OK.getHttpStatus())
                .body(SuccessResponse.of(SuccessCode.OK, body));
    }

    @GetMapping("reports")
    public ResponseEntity<SuccessResponse<ReportListData>> list(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        LocalDateTime start = month.atDay(1).atStartOfDay();
        LocalDateTime end   = month.plusMonths(1).atDay(1).atStartOfDay();

        LocalDate startDate = start.toLocalDate();
        LocalDate endDateExclusive = end.toLocalDate();

        Pageable pageable   = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "reportDate"));

        PageResponse<ReportResponse> pageDto =
                reportQueryService.getReportPage(userId, startDate, endDateExclusive, pageable);

        return ResponseEntity
                .status(SuccessCode.OK.getHttpStatus())
                .body(SuccessResponse.of(SuccessCode.OK, new ReportListData(pageDto)));
    }

    @GetMapping("reports/{id}")
    public ResponseEntity<SuccessResponse<ReportResponse>> getReport(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id
    ){
        ReportResponse report = reportQueryService.getById(id);

        return ResponseEntity
                .status(SuccessCode.OK.getHttpStatus())
                .body(SuccessResponse.of(SuccessCode.OK, report));
    }
}
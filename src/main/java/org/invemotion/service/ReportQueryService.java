package org.invemotion.service;

import lombok.RequiredArgsConstructor;
import org.invemotion.domain.report.Report;
import org.invemotion.dto.response.ReportResponse;
import org.invemotion.global.dto.PageResponse;
import org.invemotion.global.dto.enums.ErrorCode;
import org.invemotion.global.exception.CustomException;
import org.invemotion.repository.ReportRepository;
import org.invemotion.service.validator.QueryValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportQueryService {

    private final ReportRepository reportRepository;
    private final QueryValidator validator;

    public PageResponse<ReportResponse> getReportPage(
            Long userId,
            LocalDate start,
            LocalDate end,
            Pageable pageable
    ){
        validator.validateUser(userId);
        validator.validatePeriod(start, end);

        Page<Report> p = reportRepository.findPageByUserAndDateBetween(userId, start, end, pageable);

        List<Report> reports = p.getContent();

        List<ReportResponse> items = reports.stream()
                .map(this::toDto)
                .toList();

        return new PageResponse<>(items, p.getNumber(), p.getSize(), p.getTotalElements(), p.isLast());


    }

    private ReportResponse toDto(Report r){
        return new ReportResponse(
                r.getId(),
                r.getUser().getUserId(),
                r.getPeriodType(),
                r.getReportDate(),
                r.getContent(),
                r.getMeta(),
                r.getCreatedAt(),
                r.getUpdatedAt()
        );
    };

    public ReportResponse getById(Long id){
        var r = reportRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.REPORT_NOT_FOUND_OR_NOT_OWNED));
        return new ReportResponse(
                r.getId(),
                r.getUser().getUserId(),
                r.getPeriodType(),
                r.getReportDate(),
                r.getContent(),
                r.getMeta(),
                r.getCreatedAt(),
                r.getUpdatedAt()
        );

    }
}

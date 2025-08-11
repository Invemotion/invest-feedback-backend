package org.invemotion.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.invemotion.domain.journal.Journal;
import org.invemotion.domain.report.Report;
import org.invemotion.domain.report.enums.PeriodType;
import org.invemotion.domain.user.User;
import org.invemotion.dto.request.JournalUpdateRequest;
import org.invemotion.dto.request.ReportCreateRequest;
import org.invemotion.dto.request.ReportUpdateRequest;
import org.invemotion.global.dto.enums.ErrorCode;
import org.invemotion.global.exception.CustomException;
import org.invemotion.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportCommandService {

    private final ReportRepository reportRepository;
    private final EntityManager em;

    @Transactional
    public Report create(Long userId, ReportCreateRequest req) {

        if (existsByPeriod(userId, req.periodType(), req.reportDate())) {
            throw new CustomException(ErrorCode.REPORT_ALREADY_EXISTS);
        }

        User userRef = em.getReference(User.class, userId);

        Report report = Report.builder()
                .user(userRef)
                .periodType(req.periodType())
                .reportDate(req.reportDate())
                .content(req.content())
                .meta(req.meta())
                .build();

        return reportRepository.save(report);
    }

    @Transactional
    public void update(Long userId, Long id, ReportUpdateRequest req){
        validateReportOwnership(id, userId);

        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.REPORT_NOT_FOUND_OR_NOT_OWNED));

        boolean changed = report.update(req.content(),req.meta());
        if (!changed) {
            throw new CustomException(ErrorCode.REPORT_NOT_MODIFIED);
        }
    }

    private boolean existsByPeriod(Long userId, PeriodType type, LocalDate date) {
        return reportRepository.existsByUser_UserIdAndPeriodTypeAndReportDate(userId, type, date);
    }

    private void validateReportOwnership(Long id, Long userId) {
        boolean owned = reportRepository.existsByIdAndUser_UserId(id, userId);
        if (!owned) throw new CustomException(ErrorCode.REPORT_NOT_FOUND_OR_NOT_OWNED);
    }
}

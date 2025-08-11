package org.invemotion.repository;

import org.invemotion.domain.report.Report;
import org.invemotion.domain.report.enums.PeriodType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("""
        select r from Report r
        where r.user.userId = :userId
          and r.reportDate is not null
          and r.reportDate >= :start
          and r.reportDate <  :end
    """)
    Page<Report> findPageByUserAndDateBetween(Long userId, LocalDate start, LocalDate end, Pageable pageable);


    boolean existsByUser_UserIdAndPeriodTypeAndReportDate(Long userId, PeriodType type, LocalDate date);

    boolean existsByIdAndUser_UserId(Long id, Long userId);
}

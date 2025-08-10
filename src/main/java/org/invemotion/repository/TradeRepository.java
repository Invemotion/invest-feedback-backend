package org.invemotion.repository;

import org.invemotion.domain.trade.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query("""
        select t from Trade t
        where t.user.userId = :userId
          and t.completedTime is not null
          and t.completedTime >= :start
          and t.completedTime <  :end
    """)
    Page<Trade> findPageByUserAndCompletedBetween(Long userId, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
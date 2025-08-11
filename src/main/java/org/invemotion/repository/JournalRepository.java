package org.invemotion.repository;

import org.invemotion.domain.journal.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JournalRepository extends JpaRepository<Journal, Long> {

    @Query("""
        select j.trade.id as tradeId, j.id as id
        from Journal j
        where j.trade.id in :tradeIds
    """)
    List<TradeJournalPair> findJournalIdsByTradeIds(Collection<Long> tradeIds);

    boolean existsByIdAndUser_UserId(Long id, Long userId);

    interface TradeJournalPair {
        Long getTradeId();
        Long getId();
    }
}

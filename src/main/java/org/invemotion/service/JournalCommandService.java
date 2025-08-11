package org.invemotion.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.invemotion.domain.journal.Journal;
import org.invemotion.domain.trade.Trade;
import org.invemotion.domain.user.User;
import org.invemotion.dto.request.JournalCreateRequest;
import org.invemotion.dto.request.JournalUpdateRequest;
import org.invemotion.global.dto.enums.ErrorCode;
import org.invemotion.global.exception.CustomException;
import org.invemotion.repository.JournalRepository;
import org.invemotion.repository.TradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JournalCommandService {

    private final TradeRepository tradeRepository;
    private final JournalRepository journalRepository;
    private final EntityManager em;

    @Transactional
    public Journal create(Long userId, Long tradeId, JournalCreateRequest req) {
        validateTradeOwnership(tradeId, userId);
        User  userRef  = em.getReference(User.class,  userId);
        Trade tradeRef = em.getReference(Trade.class, tradeId);

        Journal journal = Journal.builder()
                .user(userRef)
                .trade(tradeRef)
                .reason(req.reason())
                .emotion(req.emotion())
                .behavior(req.behavior())
                .build();

        return journalRepository.save(journal);
    }

    @Transactional
    public void update(Long userId, Long id, JournalUpdateRequest req){
        validateJournalOwnership(id, userId);

        Journal journal = journalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.JOURNAL_NOT_FOUND_OR_NOT_OWNED));

        boolean changed = journal.update(req.reason(), req.emotion(), req.behavior());
        if (!changed) {
            throw new CustomException(ErrorCode.JOURNAL_NOT_MODIFIED);
        }
    }

    private void validateTradeOwnership(Long tradeId, Long userId) {
        boolean owned = tradeRepository.existsByIdAndUser_UserId(tradeId, userId);
        if (!owned) throw new CustomException(ErrorCode.TRADE_NOT_FOUND_OR_NOT_OWNED);
    }

    private void validateJournalOwnership(Long id, Long userId) {
        boolean owned = journalRepository.existsByIdAndUser_UserId(id, userId);
        if (!owned) throw new CustomException(ErrorCode.JOURNAL_NOT_FOUND_OR_NOT_OWNED);
    }
}

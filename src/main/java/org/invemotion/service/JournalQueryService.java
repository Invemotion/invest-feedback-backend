package org.invemotion.service;

import lombok.RequiredArgsConstructor;
import org.invemotion.domain.journal.Journal;
import org.invemotion.dto.response.JournalResponse;
import org.invemotion.global.dto.enums.ErrorCode;
import org.invemotion.global.exception.CustomException;
import org.invemotion.repository.JournalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JournalQueryService {

    private final JournalRepository journalRepository;

    public JournalResponse getById(Long id){
        var j = journalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.JOURNAL_NOT_FOUND_OR_NOT_OWNED));
        return new JournalResponse(
                j.getId(),
                j.getTrade().getId(),
                j.getUser().getUserId(),
                j.getReason(),
                j.getEmotion(),
                j.getBehavior(),
                j.getCreatedAt(),
                j.getUpdatedAt()
        );
    }
}

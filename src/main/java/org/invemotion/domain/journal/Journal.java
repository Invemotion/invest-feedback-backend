package org.invemotion.domain.journal;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.invemotion.domain.journal.enums.*;
import org.invemotion.domain.trade.Trade;
import org.invemotion.domain.user.User;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "journals")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id", nullable = false)
    private Trade trade;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "emotion", nullable = false)
    private Emotion emotion;

    @Enumerated(EnumType.STRING)
    @Column(name = "behavior", nullable = false)
    private BehaviorType behavior;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}


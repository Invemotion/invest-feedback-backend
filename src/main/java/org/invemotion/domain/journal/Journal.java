package org.invemotion.domain.journal;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.invemotion.domain.journal.enums.*;
import org.invemotion.domain.trade.Trade;
import org.invemotion.domain.user.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;


    @PrePersist
    @PreUpdate
    private void normalize() {
        if (reason != null) {
            reason = reason.trim();
        }
    }

    public boolean update(String reason, Emotion emotion, BehaviorType behavior) {
        boolean changed = false;

        if (!this.reason.equals(reason)) {
            this.reason = reason;
            changed = true;
        }
        if (this.emotion != emotion) {
            this.emotion = emotion;
            changed = true;
        }
        if (this.behavior != behavior) {
            this.behavior = behavior;
            changed = true;
        }

        return changed;
    }
}


package org.invemotion.domain.report;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.invemotion.domain.report.enums.PeriodType;
import org.invemotion.domain.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "reports")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "period_type", nullable = false)
    private PeriodType periodType;

    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Type(JsonType.class)
    @Column(name = "meta", columnDefinition = "JSONB")
    private JsonNode meta;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (content != null) {
            String trimmed = content.trim();
            if (!trimmed.equals(content)) {
                content = trimmed;
            }
        }
    }

    public boolean update(String content, com.fasterxml.jackson.databind.JsonNode meta){
        boolean changed = false;

        if (content != null) {
            String newContent = content.trim();
            if (!java.util.Objects.equals(this.content, newContent)) {
                this.content = newContent;
                changed = true;
            }
        }
        if (meta != null) {
            if (!java.util.Objects.equals(this.meta, meta)) {
                this.meta = meta;
                changed = true;
            }
        }
        return changed;
    }
}

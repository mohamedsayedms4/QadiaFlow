package org.example.qadiaflow.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author Mohamed Sayed
 * @date 2026-01-31
 */
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(
        name = "sessions",
        indexes = {
                @Index(name = "idx_sessions_case_date", columnList = "case_id, session_date"),
                @Index(name = "idx_sessions_court", columnList = "court_id")
        }
)
public class Session extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    // Case "1" o-- "0..*" Session
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "case_id", nullable = false)
    private Case caseRef;

    @Column(name = "session_date", nullable = false)
    private LocalDateTime sessionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id")
    private Court court;

    private String circuit;

    private String result;

    @Column(name = "next_session_date")
    private LocalDateTime nextSessionDate;

    private String notes;

    @Override
    protected void beforePersist() {
        if (tenant == null && caseRef != null) {
            tenant = caseRef.getTenant();
        }
    }
}

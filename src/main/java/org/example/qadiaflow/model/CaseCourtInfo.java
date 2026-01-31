package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Mohamed Sayed
 * @date 2026-01-31
 */
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(
        name = "case_court_info",
        uniqueConstraints = {
                // لكل قضية: درجة واحدة فقط (First/Appeal/...)
                @UniqueConstraint(columnNames = {"case_id", "degree"})
        },
        indexes = {
                @Index(name = "idx_case_court_case", columnList = "case_id"),
                @Index(name = "idx_case_court_court", columnList = "court_id")
        }
)
public class CaseCourtInfo extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "case_id", nullable = false)
    private Case caseRef;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CaseDegree degree;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    private String circuit;
    private String caseNumber;
    private Integer caseYear;

    @Override
    protected void beforePersist() {
        if (tenant == null && caseRef != null) {
            tenant = caseRef.getTenant();
        }
    }
}

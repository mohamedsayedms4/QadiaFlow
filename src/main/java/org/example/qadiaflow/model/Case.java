package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;

import lombok.experimental.SuperBuilder;

/**
 * @author Mohamed Sayed
 * @date 2026-01-31
 */
@Entity
@Table(
        name = "cases",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tenant_id", "internal_code"})
        },
        indexes = {
                @Index(name = "idx_cases_tenant_status", columnList = "tenant_id, status"),
                @Index(name = "idx_cases_tenant_category", columnList = "tenant_id, category_id"),
                @Index(name = "idx_cases_tenant_responsible", columnList = "tenant_id, responsible_user_id")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class Case extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(name = "internal_code", nullable = false, length = 60)
    private String internalCode;

    @Column(length = 500)
    private String subject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CaseStatus status;

    // User "1" --> "0..*" Case : responsible for
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "responsible_user_id", nullable = false)
    private User responsibleUser;

    // CaseCategory "1" --> "0..*" Case : categorizes
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private CaseCategory category;

    @Override
    protected void beforePersist() {
        if (status == null) status = CaseStatus.NEW;
    }
}

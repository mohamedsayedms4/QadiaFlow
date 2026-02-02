package org.example.qadiaflow.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * @author Mohamed Sayed
 * @date 2026-01-31
 */
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(
        name = "fee_plans",
        indexes = {
                @Index(name = "idx_fee_plans_tenant_case", columnList = "tenant_id, case_id"),
                @Index(name = "idx_fee_plans_tenant_client", columnList = "tenant_id, client_id"),
                @Index(name = "idx_fee_plans_tenant_status", columnList = "tenant_id, status")
        }
)
public class FeePlan extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    // Case "1" o-- "0..*" FeePlan
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "case_id", nullable = false)
    private Case caseRef;

    // FeePlan "1" --> "1" Client : billed to
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false, length = 10)
    private String currency; // "EGP", "USD"

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FeePlanStatus status;

    @Override
    protected void beforePersist() {
        if (tenant == null && caseRef != null) tenant = caseRef.getTenant();
        if (status == null) status = FeePlanStatus.ACTIVE;
        if (currency != null && currency.isBlank()) currency = null;
    }
}

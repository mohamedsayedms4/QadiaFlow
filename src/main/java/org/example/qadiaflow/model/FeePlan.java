package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

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

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "case_id", nullable = false)
    private Long caseId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(nullable = false, length = 10)
    private String currency; // e.g. "EGP", "USD"

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FeePlanStatus status;

    @PrePersist
    protected void defaults() {
        if (status == null) status = FeePlanStatus.ACTIVE;
        if (currency != null && currency.isBlank()) currency = null;
    }
}

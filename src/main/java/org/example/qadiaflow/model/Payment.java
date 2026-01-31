package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
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
        name = "payments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tenant_id", "receipt_no"})
        },
        indexes = {
                @Index(name = "idx_payments_tenant_plan", columnList = "tenant_id, fee_plan_id"),
                @Index(name = "idx_payments_paid_at", columnList = "paid_at")
        }
)
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    // FeePlan "1" o-- "0..*" Payment
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fee_plan_id", nullable = false)
    private FeePlan feePlan;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentMethod method;

    @Column(name = "receipt_no", length = 80)
    private String receiptNo;

    @Column(name = "paid_at", nullable = false)
    private LocalDateTime paidAt;

    @Column(name = "recorded_by_user_id", nullable = false)
    private Long recordedByUserId;

    @Override
    protected void beforePersist() {
        if (tenant == null && feePlan != null) tenant = feePlan.getTenant();
        if (paidAt == null) paidAt = LocalDateTime.now();
        if (receiptNo != null && receiptNo.isBlank()) receiptNo = null;
    }
}

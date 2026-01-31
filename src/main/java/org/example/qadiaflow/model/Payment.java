package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(
        name = "payments",
        uniqueConstraints = {
                // يمنع تكرار رقم إيصال داخل نفس التينانت (اختياري حسب نظامك)
                @UniqueConstraint(columnNames = {"tenant_id", "receipt_no"})
        },
        indexes = {
                @Index(name = "idx_payments_tenant_plan", columnList = "tenant_id, fee_plan_id"),
                @Index(name = "idx_payments_paid_at", columnList = "paid_at")
        }
)
public class Payment extends BaseEntity {

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "fee_plan_id", nullable = false)
    private Long feePlanId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentMethod method;

    @Column(name = "receipt_no", length = 80)
    private String receiptNo;

    @Column(name = "paid_at", nullable = false)
    private LocalDateTime paidAt;

    /**
     * Who recorded the payment (User ID).
     * BaseEntity.createdBy is String in your project, so we keep this as Long.
     */
    @Column(name = "recorded_by_user_id", nullable = false)
    private Long recordedByUserId;

    @Override
    protected void beforePersist() {
        if (paidAt == null) paidAt = LocalDateTime.now();
        if (receiptNo != null && receiptNo.isBlank()) receiptNo = null;
    }
}

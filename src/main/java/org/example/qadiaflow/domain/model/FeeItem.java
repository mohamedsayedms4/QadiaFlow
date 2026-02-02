package org.example.qadiaflow.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Mohamed Sayed
 * @date 2026-01-31
 */
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(
        name = "fee_items",
        indexes = {
                @Index(name = "idx_fee_items_plan", columnList = "fee_plan_id"),
                @Index(name = "idx_fee_items_status_due", columnList = "status, due_date")
        }
)
public class FeeItem extends BaseEntity {

    // FeePlan "1" o-- "0..*" FeeItem
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fee_plan_id", nullable = false)
    private FeePlan feePlan;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 100)
    private String stage;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FeeItemStatus status;

    @Override
    protected void beforePersist() {
        if (status == null) status = FeeItemStatus.DUE;
        if (stage != null && stage.isBlank()) stage = null;
    }
}

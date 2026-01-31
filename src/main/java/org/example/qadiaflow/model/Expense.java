package org.example.qadiaflow.model;

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
        name = "expenses",
        indexes = {
                @Index(name = "idx_expenses_tenant_case", columnList = "tenant_id, case_id"),
                @Index(name = "idx_expenses_date", columnList = "expense_date")
        }
)
public class Expense extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    // Case "1" o-- "0..*" Expense : expenses
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "case_id", nullable = false)
    private Case caseRef;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(name = "expense_type", length = 80)
    private String expenseType;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    // Expense "0..1" --> "1" Document : attachment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_doc_id")
    private Document attachment;

    @Override
    protected void beforePersist() {
        if (tenant == null && caseRef != null) {
            tenant = caseRef.getTenant();
        }
    }
}

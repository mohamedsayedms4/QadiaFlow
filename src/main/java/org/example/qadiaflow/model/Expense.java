package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "case_id", nullable = false)
    private Long caseId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(name = "expense_type", length = 80)
    private String expenseType;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    @Column(name = "attachment_doc_id")
    private Long attachmentDocId;
}

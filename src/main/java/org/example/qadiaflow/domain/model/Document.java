package org.example.qadiaflow.domain.model;

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
        name = "documents",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tenant_id", "storage_key", "version"})
        },
        indexes = {
                @Index(name = "idx_documents_tenant_case", columnList = "tenant_id, case_id"),
                @Index(name = "idx_documents_tenant_contract", columnList = "tenant_id, contract_id")
        }
)
public class Document extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    // Case "1" o-- "0..*" Document : documents
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    private Case caseRef;

    // Contract "1" o-- "0..*" Document : attachments
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 120)
    private String mime;

    @Column(nullable = false)
    private Long size;

    @Column(name = "storage_key", nullable = false, length = 500)
    private String storageKey;

    @Column(nullable = false)
    private Integer version;

    @Column(length = 500)
    private String tags;

    @Override
    protected void beforePersist() {
        // Auto-derive tenant if missing (from case/contract)
        if (tenant == null) {
            if (caseRef != null) tenant = caseRef.getTenant();
            else if (contract != null) tenant = contract.getTenant();
        }

        if (version == null) version = 1;
        if (tags != null && tags.isBlank()) tags = null;
    }
}

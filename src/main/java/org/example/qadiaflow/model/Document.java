package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "case_id")
    private Long caseId;

    @Column(name = "contract_id")
    private Long contractId;

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

    /**
     * Comma-separated tags (e.g. "contract,scan,important")
     */
    @Column(length = 500)
    private String tags;


}

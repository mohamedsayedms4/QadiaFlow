package org.example.qadiaflow.model;

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
        name = "contract_versions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"contract_id", "version"}),
                @UniqueConstraint(columnNames = {"document_id"}) // يضمن 1..1 فعلاً
        },
        indexes = {
                @Index(name = "idx_contract_versions_contract", columnList = "contract_id"),
                @Index(name = "idx_contract_versions_document", columnList = "document_id")
        }
)
public class ContractVersion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Column(nullable = false)
    private Integer version;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", nullable = false, unique = true)
    private Document document;

    @Override
    protected void beforePersist() {
        if (version == null) version = 1;
    }
}

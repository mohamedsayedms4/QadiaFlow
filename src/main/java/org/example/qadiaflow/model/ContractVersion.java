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
        name = "contract_versions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"contract_id", "version"})
        },
        indexes = {
                @Index(name = "idx_contract_versions_contract", columnList = "contract_id"),
                @Index(name = "idx_contract_versions_document", columnList = "document_id")
        }
)
public class ContractVersion extends BaseEntity {

    @Column(name = "contract_id", nullable = false)
    private Long contractId;

    @Column(nullable = false)
    private Integer version;

    @Column(name = "document_id", nullable = false)
    private Long documentId;


}

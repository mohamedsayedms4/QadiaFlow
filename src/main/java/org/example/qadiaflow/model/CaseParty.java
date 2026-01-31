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
        name = "case_parties",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"case_id", "client_id", "role"})
        },
        indexes = {
                @Index(name = "idx_case_parties_case", columnList = "case_id"),
                @Index(name = "idx_case_parties_client", columnList = "client_id")
        }
)
public class CaseParty extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    // Case "1" o-- "0..*" CaseParty : parties
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "case_id", nullable = false)
    private Case caseRef;

    // CaseParty "1" --> "1" Client : client
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PartyRole role;

    private String representation;
    private String notes;

    @Override
    protected void beforePersist() {
        if (tenant == null && caseRef != null) {
            tenant = caseRef.getTenant();
        }
    }
}

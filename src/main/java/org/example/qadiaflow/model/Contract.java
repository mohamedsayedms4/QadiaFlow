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
        name = "contracts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tenant_id", "client_id", "title"})
        },
        indexes = {
                @Index(name = "idx_contracts_tenant_client", columnList = "tenant_id, client_id"),
                @Index(name = "idx_contracts_tenant_status", columnList = "tenant_id, status")
        }
)
public class Contract extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(precision = 19, scale = 2)
    private BigDecimal value;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContractStatus status;

    @Override
    protected void beforePersist() {
        if (status == null) status = ContractStatus.DRAFT;

        if (tenant == null && client != null) {
            tenant = client.getTenant();
        }
    }
}

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
@Table(name = "client_contacts",
        indexes = {
                @Index(name = "idx_client_contacts_client", columnList = "client_id")
        }
)
public class ClientContact extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private String phone;
    private String email;
    private String address;

    private Boolean isPrimary;

    @Override
    protected void beforePersist() {
        if (tenant == null && client != null) {
            tenant = client.getTenant();
        }
        if (isPrimary == null) isPrimary = false;
    }
}

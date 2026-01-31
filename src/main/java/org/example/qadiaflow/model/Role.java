package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Mohamed Sayed
 * @date 2026-01-31
 */
@Entity
@Table(
        name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tenant_id", "name"})
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@lombok.experimental.SuperBuilder
public class Role extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(length = 255)
    private String description;
}

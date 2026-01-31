package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "role_permissions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tenant_id", "role_id", "permission_id"})
        },
        indexes = {
                @Index(name = "idx_role_permissions_role", columnList = "role_id"),
                @Index(name = "idx_role_permissions_perm", columnList = "permission_id")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class RolePermission extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    // Role "1" o-- "0..*" RolePermission : grants
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    // RolePermission "1" --> "1" Permission : permission
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    @Override
    protected void beforePersist() {
        if (tenant == null && role != null) {
            tenant = role.getTenant();
        }
    }
}

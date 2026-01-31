package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "role_permissions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tenant_id", "role_id", "permission_code"})
        }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RolePermission extends BaseEntity {

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "permission_code", nullable = false, length = 120)
    private String permissionCode;
}

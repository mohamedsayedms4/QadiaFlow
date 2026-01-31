package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(
        name = "audit_logs",
        indexes = {
                @Index(name = "idx_audit_tenant_created", columnList = "tenant_id, created_at"),
                @Index(name = "idx_audit_actor", columnList = "tenant_id, actor_id"),
                @Index(name = "idx_audit_entity", columnList = "tenant_id, entity_type, entity_id"),
                @Index(name = "idx_audit_action", columnList = "action")
        }
)
public class AuditLog extends BaseEntity {

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "actor_id", nullable = false)
    private Long actorId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private AuditAction action;

    @Column(name = "entity_type", nullable = false, length = 120)
    private String entityType;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Lob
    @Column(name = "before_json")
    private String beforeJson;

    @Lob
    @Column(name = "after_json")
    private String afterJson;

    @Column(length = 45)
    private String ip;
}

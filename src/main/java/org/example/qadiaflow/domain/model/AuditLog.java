package org.example.qadiaflow.domain.model;

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
        name = "audit_logs",
        indexes = {
                @Index(name = "idx_audit_tenant_created", columnList = "tenant_id, created_at"),
                @Index(name = "idx_audit_actor", columnList = "tenant_id, actor_id"),
                @Index(name = "idx_audit_entity", columnList = "tenant_id, entity_type, entity_id"),
                @Index(name = "idx_audit_action", columnList = "action")
        }
)
public class AuditLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    // User "1" --> "0..*" AuditLog : acts
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "actor_id", nullable = false)
    private User actor;

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

    @Override
    protected void beforePersist() {
        if (tenant == null && actor != null) {
            tenant = actor.getTenant();
        }
    }
}

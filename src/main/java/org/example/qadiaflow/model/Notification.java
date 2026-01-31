package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author Mohamed Sayed
 * @date 2026-01-31
 */
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(
        name = "notifications",
        indexes = {
                @Index(name = "idx_notifications_tenant_user", columnList = "tenant_id, user_id"),
                @Index(name = "idx_notifications_read_at", columnList = "read_at"),
                @Index(name = "idx_notifications_type", columnList = "type")
        }
)
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    // User "1" o-- "0..*" Notification : receives
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 2000)
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationType type;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Override
    protected void beforePersist() {
        // derive tenant automatically from user if not set
        if (tenant == null && user != null) {
            tenant = user.getTenant();
        }
    }
}

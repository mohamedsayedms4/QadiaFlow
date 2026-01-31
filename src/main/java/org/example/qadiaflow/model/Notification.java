package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 2000)
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationType type;

    @Column(name = "read_at")
    private LocalDateTime readAt;
}

package org.example.qadiaflow.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author Mohamed Sayed
 * @date 2026-01-31
 */
@MappedSuperclass
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(updatable = false)
    private String createdBy;

    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        beforePersist();       // <-- hook
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        beforeUpdate();        // <-- hook
    }

    /** Override in subclasses if needed (no JPA annotations here). */
    protected void beforePersist() {}

    /** Override in subclasses if needed (no JPA annotations here). */
    protected void beforeUpdate() {}
}

package org.example.qadiaflow.domain.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Mohamed Sayed
 * @date 2026-01-31
 */
@Entity
@Table(
        name = "permissions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code"})
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@lombok.experimental.SuperBuilder
public class Permission extends BaseEntity {

    @Column(nullable = false, length = 120)
    private String code;

    @Column(length = 255)
    private String description;
}

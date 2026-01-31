package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;

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

package org.example.qadiaflow.model;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Court extends BaseEntity {

    private Long tenantId;
    private String name;
    private String city;
    private Boolean isActive;
}

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
public class CaseCategory extends BaseEntity {

    private Long tenantId;
    private String name;
    private Boolean isActive;
}

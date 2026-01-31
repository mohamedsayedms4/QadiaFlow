package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(name = "cases")
public class Case extends BaseEntity {

    private Long tenantId;
    private String internalCode;
    private String subject;

    @Enumerated(EnumType.STRING)
    private CaseStatus status;

    private Long responsibleUserId;
    private Long categoryId;
}

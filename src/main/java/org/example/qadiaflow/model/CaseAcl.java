package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(name = "case_acl")
public class CaseAcl extends BaseEntity {

    private Long caseId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;
}

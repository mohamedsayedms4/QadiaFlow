package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(name = "case_parties")
public class CaseParty extends BaseEntity {

    private Long caseId;
    private Long clientId;

    @Enumerated(EnumType.STRING)
    private PartyRole role;

    private String representation;
    private String notes;
}

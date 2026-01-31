package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(name = "case_court_info")
public class CaseCourtInfo extends BaseEntity {

    private Long caseId;

    @Enumerated(EnumType.STRING)
    private CaseDegree degree;

    private Long courtId;

    private String circuit;     // كان ناقص
    private String caseNumber;
    private Integer caseYear;
}

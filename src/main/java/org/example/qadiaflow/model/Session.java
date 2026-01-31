package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(name = "sessions")
public class Session extends BaseEntity {

    private Long tenantId;
    private Long caseId;

    private LocalDateTime sessionDate;

    private Long courtId;
    private String circuit;

    private String result;
    private LocalDateTime nextSessionDate;
    private String notes;
}

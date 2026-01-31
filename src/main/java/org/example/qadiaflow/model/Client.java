package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(name = "clients")
public class Client extends BaseEntity {

    private Long tenantId;

    @Enumerated(EnumType.STRING)
    private ClientType type;

    private String name;
    private String nationalId;
    private String notes;
}

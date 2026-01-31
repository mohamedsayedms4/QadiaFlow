package org.example.qadiaflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(name = "client_contacts")
public class ClientContact extends BaseEntity {

    private Long tenantId;

    private Long clientId;

    private String phone;
    private String email;
    private String address;

    private Boolean isPrimary;
}

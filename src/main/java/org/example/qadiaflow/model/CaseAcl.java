package org.example.qadiaflow.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Table(
        name = "case_acl",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"case_id", "user_id"})
        },
        indexes = {
                @Index(name = "idx_case_acl_case", columnList = "case_id"),
                @Index(name = "idx_case_acl_user", columnList = "user_id"),
                @Index(name = "idx_case_acl_level", columnList = "access_level")
        }
)
public class CaseAcl extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    // Case "1" o-- "0..*" CaseAcl : access
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "case_id", nullable = false)
    private Case caseRef;

    // CaseAcl "1" --> "1" User : user
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_level", nullable = false, length = 30)
    private AccessLevel accessLevel;

    @Override
    protected void beforePersist() {
        if (tenant == null && caseRef != null) {
            tenant = caseRef.getTenant();
        }
        if (accessLevel == null) accessLevel = AccessLevel.VIEW;
    }
}

package org.example.qadiaflow.infrastructure.persistence.jpa;

import org.example.qadiaflow.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByTenant_IdAndName(Long tenantId, String name);
}

package org.example.qadiaflow.application.port.out;

import org.example.qadiaflow.domain.model.Role;

import java.util.Optional;

public interface RolePort {
    Optional<Role> findByTenantAndName(Long tenantId, String name);
    Role save(Role role);
}

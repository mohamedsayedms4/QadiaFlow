package org.example.qadiaflow.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.example.qadiaflow.application.port.out.RolePort;
import org.example.qadiaflow.domain.model.Role;
import org.example.qadiaflow.infrastructure.persistence.jpa.JpaRoleRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleAdapter implements RolePort {

    private final JpaRoleRepository repo;

    @Override
    public Optional<Role> findByTenantAndName(Long tenantId, String name) {
        return repo.findByTenant_IdAndName(tenantId, name);
    }

    @Override
    public Role save(Role role) {
        return repo.save(role);
    }
}

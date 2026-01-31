package org.example.qadiaflow.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.example.qadiaflow.application.port.out.UserRolePort;
import org.example.qadiaflow.domain.model.Role;
import org.example.qadiaflow.domain.model.Tenant;
import org.example.qadiaflow.domain.model.User;
import org.example.qadiaflow.domain.model.UserRole;
import org.example.qadiaflow.infrastructure.persistence.jpa.JpaUserRoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserRoleAdapter implements UserRolePort {

    private final JpaUserRoleRepository repo;

    @Override
    public void assignRole(Tenant tenant, User user, Role role) {
        boolean exists = repo.existsByTenant_IdAndUser_IdAndRole_Id(tenant.getId(), user.getId(), role.getId());
        if (exists) return;

        repo.save(UserRole.builder()
                .tenant(tenant)
                .user(user)
                .role(role)
                .build());
    }

    @Override
    public List<String> findRoleNames(Long tenantId, Long userId) {
        return repo.findRoleNames(tenantId, userId);
    }
}

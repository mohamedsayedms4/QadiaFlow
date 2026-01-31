package org.example.qadiaflow.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.example.qadiaflow.application.port.out.UserPort;
import org.example.qadiaflow.domain.model.User;
import org.example.qadiaflow.infrastructure.persistence.jpa.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserPort {

    private final JpaUserRepository repo;

    @Override
    public Optional<User> findByTenantAndUsernameOrEmail(Long tenantId, String usernameOrEmail) {
        return repo.findByTenantAndUsernameOrEmail(tenantId, usernameOrEmail);
    }

    @Override
    public boolean existsByTenantAndUsername(Long tenantId, String username) {
        return repo.existsByTenant_IdAndUsernameIgnoreCase(tenantId, username);
    }

    @Override
    public boolean existsByTenantAndEmail(Long tenantId, String email) {
        return repo.existsByTenant_IdAndEmailIgnoreCase(tenantId, email);
    }

    @Override
    public boolean existsByTenantAndPhone(Long tenantId, String phone) {
        return repo.existsByTenant_IdAndPhoneIgnoreCase(tenantId , phone);
    }

    @Override
    public User save(User user) {
        return repo.save(user);
    }
}

package org.example.qadiaflow.application.port.out;

import org.example.qadiaflow.domain.model.User;

import java.util.Optional;

public interface UserPort {
    Optional<User> findByTenantAndUsernameOrEmail(Long tenantId, String usernameOrEmail);

    boolean existsByTenantAndUsername(Long tenantId, String username);
    boolean existsByTenantAndEmail(Long tenantId, String email);
    boolean existsByTenantAndPhone(Long tenantId, String phone);
    User save(User user);
}

package org.example.qadiaflow.infrastructure.persistence.jpa;

import org.example.qadiaflow.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    boolean existsByTenant_IdAndUsernameIgnoreCase(Long tenantId, String username);
    boolean existsByTenant_IdAndEmailIgnoreCase(Long tenantId, String email);
    boolean existsByTenant_IdAndPhoneIgnoreCase(Long tenetId , String phone);
    @Query("""
      select u from User u
      where u.tenant.id = :tenantId
        and (lower(u.username) = lower(:x) or lower(u.email) = lower(:x))
    """)
    Optional<User> findByTenantAndUsernameOrEmail(Long tenantId, String x);
}

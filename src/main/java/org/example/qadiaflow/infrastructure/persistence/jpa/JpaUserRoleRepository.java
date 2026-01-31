package org.example.qadiaflow.infrastructure.persistence.jpa;

import org.example.qadiaflow.domain.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaUserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("""
      select r.name
      from UserRole ur
      join ur.role r
      where ur.tenant.id = :tenantId and ur.user.id = :userId
    """)
    List<String> findRoleNames(Long tenantId, Long userId);

    boolean existsByTenant_IdAndUser_IdAndRole_Id(Long tenantId, Long userId, Long roleId);
}

package org.example.qadiaflow.application.port.out;

import org.example.qadiaflow.domain.model.Role;
import org.example.qadiaflow.domain.model.Tenant;
import org.example.qadiaflow.domain.model.User;

import java.util.List;

public interface UserRolePort {
    void assignRole(Tenant tenant, User user, Role role);
    List<String> findRoleNames(Long tenantId, Long userId);
}

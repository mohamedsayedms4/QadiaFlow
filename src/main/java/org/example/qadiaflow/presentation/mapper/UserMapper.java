package org.example.qadiaflow.presentation.mapper;

import org.example.qadiaflow.domain.model.Tenant;
import org.example.qadiaflow.domain.model.User;
import org.example.qadiaflow.domain.model.UserStatus;
import org.example.qadiaflow.presentation.dto.auth.RegisterRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Maps RegisterRequest to User.
     * Tenant is provided by caller (already loaded/validated).
     *
     * Notes:
     * - passwordHash is ignored here (it must be set after encoding).
     * - status defaulted to ACTIVE (can be overridden by caller).
     * - lastLoginAt ignored (null on register).
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)

    @Mapping(target = "tenant", source = "tenant")
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "status", expression = "java(defaultStatus())")
    @Mapping(target = "lastLoginAt", ignore = true)
    User toNewUser(RegisterRequest req, Tenant tenant);

    default UserStatus defaultStatus() {
        return UserStatus.ACTIVE;
    }
}

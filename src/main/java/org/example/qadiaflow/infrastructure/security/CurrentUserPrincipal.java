package org.example.qadiaflow.infrastructure.security;

import java.util.List;

public record CurrentUserPrincipal(
        Long userId,
        Long tenantId,
        String username,
        String email,
        List<String> roles
) {}

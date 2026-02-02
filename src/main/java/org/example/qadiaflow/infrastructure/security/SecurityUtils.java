package org.example.qadiaflow.infrastructure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static CurrentUserPrincipal currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;

        Object principal = auth.getPrincipal();
        if (principal instanceof CurrentUserPrincipal cu) return cu;

        return null;
    }

    public static String currentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;

        Object principal = auth.getPrincipal();

        if (principal instanceof CurrentUserPrincipal cu) {
            if (cu.email() != null && !cu.email().isBlank()) return cu.email();
            if (cu.username() != null && !cu.username().isBlank()) return cu.username(); // fallback
            return null;
        }

        if (principal instanceof UserDetails ud) return ud.getUsername(); // غالباً username=email
        if (principal instanceof String s) return s;

        return null;
    }

    public static Long currentTenantId() {
        CurrentUserPrincipal cu = currentUser();
        return cu != null ? cu.tenantId() : null;
    }

    public static Long currentUserId() {
        CurrentUserPrincipal cu = currentUser();
        return cu != null ? cu.userId() : null;
    }
}

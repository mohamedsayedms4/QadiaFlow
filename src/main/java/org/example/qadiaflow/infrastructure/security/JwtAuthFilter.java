package org.example.qadiaflow.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/")
                || path.startsWith("/api/v1/auth/")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.equals("/swagger-ui.html")
                || path.startsWith("/actuator/health");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            Claims claims = jwtService.parseAndValidate(token);

            Long userId = parseLongSafe(claims.getSubject()); // sub
            Long tenantId = parseLongFromClaim(claims.get("tenantId"));

            String username = asString(claims.get("username"));
            String email = asString(claims.get("email"));

            // ✅ fallback: لو email مش موجود في التوكن استخدم username
            if (email == null) email = username;

            Object rolesObj = claims.get("roles");
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (rolesObj instanceof List<?> list) {
                for (Object r : list) {
                    String role = String.valueOf(r);
                    if (role != null && !role.isBlank()) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                    }
                }
            }

            CurrentUserPrincipal principal = new CurrentUserPrincipal(
                    userId,
                    tenantId,
                    username,
                    email,
                    authorities.stream().map(a -> a.getAuthority().replace("ROLE_", "")).toList()
            );

            var auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (JwtException ex) {
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }

    // ===================== helpers =====================

    private static String asString(Object o) {
        if (o == null) return null;
        String s = String.valueOf(o);
        return s.isBlank() ? null : s;
    }

    private static Long parseLongSafe(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Long parseLongFromClaim(Object claim) {
        if (claim == null) return null;
        if (claim instanceof Number n) return n.longValue();
        if (claim instanceof String s) return parseLongSafe(s);
        return null;
    }
}

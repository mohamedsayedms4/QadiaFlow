package org.example.qadiaflow.presentation.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class AuthResponse {
    private String token;
    private Instant expiresAt;

    private Long userId;
    private Long tenantId;

    private List<String> roles;
}

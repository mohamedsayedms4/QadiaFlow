package org.example.qadiaflow.application.usecase.auth.impl;

import lombok.RequiredArgsConstructor;
import org.example.qadiaflow.application.exception.BadRequestException;
import org.example.qadiaflow.application.exception.UserAlreadyExists;
import org.example.qadiaflow.application.port.out.RolePort;
import org.example.qadiaflow.application.port.out.TenantPort;
import org.example.qadiaflow.application.port.out.UserPort;
import org.example.qadiaflow.application.port.out.UserRolePort;
import org.example.qadiaflow.application.usecase.auth.AuthService;
import org.example.qadiaflow.domain.model.Role;
import org.example.qadiaflow.domain.model.Tenant;
import org.example.qadiaflow.domain.model.User;
import org.example.qadiaflow.infrastructure.security.JwtService;
import org.example.qadiaflow.presentation.dto.auth.AuthResponse;
import org.example.qadiaflow.presentation.dto.auth.LoginRequest;
import org.example.qadiaflow.presentation.dto.auth.RegisterRequest;
import org.example.qadiaflow.presentation.i18n.MessageUtil.MessageUtil;
import org.example.qadiaflow.presentation.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserPort userPort;
    private final TenantPort tenantPort;
    private final RolePort rolePort;
    private final UserRolePort userRolePort;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtTokenService;
    private final MessageUtil msg;
    private final UserMapper userMapper;
    @Override
    @Transactional
    public AuthResponse register(RegisterRequest req) {
        Tenant tenant = tenantPort.getRequired(req.getTenantId());

        if (userPort.existsByTenantAndUsername(tenant.getId(), req.getUsername())) {
            throw new UserAlreadyExists(msg.get("user.username_exists"));
        }
        if (userPort.existsByTenantAndEmail(tenant.getId(), req.getEmail())) {
            throw new UserAlreadyExists(msg.get("user.email_exists"));
        }
        if (userPort.existsByTenantAndPhone(tenant.getId(), req.getPhone())) {
            throw new UserAlreadyExists(msg.get("user.phone_exists"));
        }

        User user = userMapper.toNewUser(req, tenant);
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));


        user = userPort.save(user);

        Role defaultRole = rolePort.findByTenantAndName(tenant.getId(), "USER")
                .orElseGet(() -> rolePort.save(Role.builder()
                        .tenant(tenant)
                        .name("USER")
                        .description("Default role")
                        .build()));

        userRolePort.assignRole(tenant, user, defaultRole);

        List<String> roles = userRolePort.findRoleNames(tenant.getId(), user.getId());

        var token = jwtTokenService.generateAccessToken(
                user.getId(),
                tenant.getId(),
                user.getUsername(),
                roles
        );

        return AuthResponse.builder()
                .token(token.token())
                .expiresAt(token.expiresAt())
                .userId(user.getId())
                .tenantId(tenant.getId())
                .roles(roles)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        User user = userPort.findByTenantAndUsernameOrEmail(req.getTenantId(), req.getUsernameOrEmail())
                .orElseThrow(() -> new BadRequestException(msg.get("auth.invalid_credentials")));

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new BadRequestException(msg.get("auth.invalid_password"));
        }

        Long tenantId = user.getTenant().getId();
        List<String> roles = userRolePort.findRoleNames(tenantId, user.getId());

        var token = jwtTokenService.generateAccessToken(
                user.getId(),
                tenantId,
                user.getUsername(),
                roles
        );

        return AuthResponse.builder()
                .token(token.token())
                .expiresAt(token.expiresAt())
                .userId(user.getId())
                .tenantId(tenantId)
                .roles(roles)
                .build();
    }
}

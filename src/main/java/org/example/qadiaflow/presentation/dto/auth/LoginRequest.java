package org.example.qadiaflow.presentation.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull(message = "{validation.login.tenantId.required}")
    @Positive(message = "{validation.login.tenantId.positive}")
    private Long tenantId;

    @NotBlank(message = "{validation.login.usernameOrEmail.required}")
    private String usernameOrEmail;

    @NotBlank(message = "{validation.login.password.required}")
    private String password;
}

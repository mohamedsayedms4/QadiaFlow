package org.example.qadiaflow.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull(message = "tenantId is required")
    @Positive(message = "tenantId must be a positive number")
    private Long tenantId;

    @NotBlank(message = "usernameOrEmail is required")
    private String usernameOrEmail;

    @NotBlank(message = "password is required")
    private String password;
}

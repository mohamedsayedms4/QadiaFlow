package org.example.qadiaflow.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class RegisterRequest {

    @NotNull(message = "{validation.register.tenantId.required}")
    @Positive(message = "{validation.register.tenantId.positive}")
    private Long tenantId;

    @NotBlank(message = "{validation.register.username.required}")
    @Size(min = 3, max = 50, message = "{validation.register.username.size}")
    private String username;

    @NotBlank(message = "{validation.register.email.required}")
    @Email(message = "{validation.register.email.valid}")
    @Size(max = 254, message = "{validation.register.email.max}")
    private String email;

    @NotBlank(message = "{validation.register.phone.required}")
    @Pattern(
            regexp = "^[+]?[-() 0-9]{7,20}$",
            message = "{validation.register.phone.valid}"
    )
    private String phone;

    @NotBlank(message = "{validation.register.password.required}")
    @Size(min = 8, max = 72, message = "{validation.register.password.size}")
    private String password;
}

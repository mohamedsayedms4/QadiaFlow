package org.example.qadiaflow.application.usecase.auth;

import org.example.qadiaflow.presentation.dto.auth.AuthResponse;
import org.example.qadiaflow.presentation.dto.auth.LoginRequest;
import org.example.qadiaflow.presentation.dto.auth.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest req);

    AuthResponse login(LoginRequest req);
}

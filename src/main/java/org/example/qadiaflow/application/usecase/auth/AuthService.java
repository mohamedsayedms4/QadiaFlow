package org.example.qadiaflow.application.usecase.auth;

import org.example.qadiaflow.presentation.dto.AuthResponse;
import org.example.qadiaflow.presentation.dto.LoginRequest;
import org.example.qadiaflow.presentation.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest req);

    AuthResponse login(LoginRequest req);
}

package org.example.qadiaflow.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.qadiaflow.application.usecase.auth.AuthService;
import org.example.qadiaflow.presentation.dto.AuthResponse;
import org.example.qadiaflow.presentation.dto.LoginRequest;
import org.example.qadiaflow.presentation.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}

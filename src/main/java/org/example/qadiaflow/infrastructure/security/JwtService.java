package org.example.qadiaflow.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    public record TokenResult(String token, Instant expiresAt) {}

    private final JwtProperties props;

    private SecretKey key() {
        byte[] bytes = props.getSecret().getBytes(StandardCharsets.UTF_8);

        // HS256 requires >= 32 bytes
        if (bytes.length < 32) {
            throw new IllegalStateException("app.jwt.secret must be at least 32 bytes for HS256");
        }
        return Keys.hmacShaKeyFor(bytes);
    }


    public TokenResult generateAccessToken(Long userId, Long tenantId, String username, List<String> roles) {
        return generateAccessToken(userId, tenantId, username, username, roles);
    }

    public TokenResult generateAccessToken(Long userId, Long tenantId, String username, String email, List<String> roles) {
        Instant now = Instant.now();
        Instant exp = now.plus(props.getTtlMinutes(), ChronoUnit.MINUTES);

        String safeEmail = (email == null || email.isBlank()) ? username : email;

        String token = Jwts.builder()
                .issuer(props.getIssuer())
                .subject(String.valueOf(userId))
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claim("tenantId", tenantId)
                .claim("username", username)
                .claim("email", safeEmail)
                .claim("roles", roles)
                .signWith(key(), Jwts.SIG.HS256)
                .compact();

        return new TokenResult(token, exp);
    }

    public Claims parseAndValidate(String token) {
        return Jwts.parser()
                .requireIssuer(props.getIssuer())
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

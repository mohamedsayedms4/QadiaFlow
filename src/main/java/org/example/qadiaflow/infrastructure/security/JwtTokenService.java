package org.example.qadiaflow.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
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
public class JwtTokenService {

    public record TokenResult(String token, Instant expiresAt) {}

    private final JwtProperties props;

    private SecretKey key() {
        // HS256 requires >= 256-bit key (32 bytes)
        return Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public TokenResult generateAccessToken(Long userId, Long tenantId, String username, List<String> roles) {
        Instant now = Instant.now();
        Instant exp = now.plus(props.getTtlMinutes(), ChronoUnit.MINUTES);

        String token = Jwts.builder()
                .issuer(props.getIssuer())
                .subject(String.valueOf(userId))
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claim("tenantId", tenantId)
                .claim("username", username)
                .claim("roles", roles)
                .signWith(key(), Jwts.SIG.HS256) // JJWT 0.13.x
                .compact();

        return new TokenResult(token, exp);
    }

    public Claims parseAndValidate(String token) throws JwtException {
        return Jwts.parser()
                .requireIssuer(props.getIssuer())
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

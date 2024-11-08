package com.intellipick.java.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final long accessTokenExpiration = 1000 * 60 * 60;
    private final long refreshTokenExpiration = 1000 * 60 * 60 * 24 * 7;
    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;

    public String createAccessToken(String username) {
        return createToken(username, accessTokenExpiration);
    }

    public String createRefreshToken(String username) {
        return createToken(username, refreshTokenExpiration);
    }

    private String createToken(String username, long expirationTime) {
        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm()))
            .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
            .verifyWith(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm()))
            .build()
            .parseSignedClaims(token)
            .getBody();
    }

    public boolean isTokenExpired(String token) {
        return validateToken(token).getExpiration().before(new Date());
    }
}

package com.hexaware.simplyfly.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // minimum 32 bytes for HS256; change for production or move to properties
    private static final String SECRET_KEY = "change_this_to_a_very_long_random_secret_32+chars";
    private static final long EXPIRATION_MS = 24L * 60 * 60 * 1000; // 24 hours

    private Key signingKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // create token
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // *** Methods your code calls (exact names) ***
    public String getEmailFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        Object r = getAllClaims(token).get("role");
        return r == null ? null : r.toString();
    }

    // validate token (simple)
    public boolean validateToken(String token) {
        try {
            getAllClaims(token); // will throw if invalid/expired
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    // helper
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

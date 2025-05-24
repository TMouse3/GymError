package com.QLGym.GymError.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

@Component
public class JwtUtil {

    @Value("${jwt.secret:yourSecretKeyForJWTGenerationAndValidationThatIsLongEnough}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // 24 hours in milliseconds
    private long expiration;

    private Key getSigningKey() {
        // Decode the base64 secret key if it's base64 encoded, otherwise use getBytes()
        // Assuming it might be base64 based on common practices, adjust if needed.
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            // If not base64, treat as plain string bytes
            byte[] keyBytes = secret.getBytes();
            return Keys.hmacShaKeyFor(keyBytes);
        }
    }

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // --- Methods required for validation and extraction ---

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        // Assuming role is stored as a String claim named 'role'
        return extractClaim(token, claims -> (String) claims.get("role"));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

} 
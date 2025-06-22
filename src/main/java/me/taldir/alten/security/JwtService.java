package me.taldir.alten.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;

@Component
public class JwtService {
    private final SecretKey secretKey;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @SuppressWarnings("deprecation")
    public String generateToken(String email, int userId) {
        return Jwts.builder()
            .setSubject(email)
            .addClaims(Map.of("userId", userId))
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }
    
    public String extractEmail(String token) {
        return Jwts.parser().verifyWith(secretKey)
            .build()
            .parseSignedClaims(token).getPayload().getSubject();
    }
    
    @SuppressWarnings("deprecation")
    public Integer extractUserId(String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("userId", Integer.class);
    }
}

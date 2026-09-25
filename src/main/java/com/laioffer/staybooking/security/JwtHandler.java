package com.laioffer.staybooking.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtHandler {

    final SecretKey signingKey;

    public JwtHandler(@Value("${staybooking.jwt.secret-key}") String secretKey) {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        signingKey = Keys.hmacShaKeyFor(bytes);
    }

    public String parsedUsername(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(signingKey)
                .compact();
    }
}

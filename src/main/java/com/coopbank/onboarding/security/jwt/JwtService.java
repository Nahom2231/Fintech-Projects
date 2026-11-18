package com.coopbank.onboarding.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "my-super-secret-key-for-onboarding-project-123456";

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(
                        StandardCharsets.UTF_8
                )
        );
    }

    public String generateAccessToken(
            String phoneNumber
    ) {

        return Jwts.builder()
                .subject(phoneNumber)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 15 * 60 * 1000
                        )
                )
                .signWith(
                        getSigningKey()
                )
                .compact();
    }

    public String generateRefreshToken(
            String phoneNumber
    ) {

        return Jwts.builder()
                .subject(phoneNumber)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 7L * 24 * 60 * 60 * 1000
                        )
                )
                .signWith(
                        getSigningKey()
                )
                .compact();
    }

       public String extractPhoneNumber(
        String token
        ) {

       return Jwts.parser()
            .verifyWith(
                    getSigningKey()
            )
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
       
        }
}
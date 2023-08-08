package com.moraes.microservices.statelessauthapi.api.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.moraes.microservices.statelessauthapi.api.exception.AuthenticationException;
import com.moraes.microservices.statelessauthapi.api.exception.ValidationException;
import com.moraes.microservices.statelessauthapi.api.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtService {

    private static final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 1;
    private static final Integer ONE_DAY_IN_HOURS = 24;

    @Value("${app.token.secret-key}")
    private String secretKey;

    public String createToken(User user) {
        var data = new HashMap<String, String>();
        data.put("id", user.getId().toString());
        data.put("username", user.getUsername());
        return Jwts.builder()
                .setClaims(data)
                .setExpiration(generExpiresAt())
                .signWith(generateSign())
                .compact();
    }

    public void validateAccessToken(String token) {
        var accessToken = extractToken(token);
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateSign())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (Exception e) {
            throw new AuthenticationException("Invalid token" + e.getMessage());
        }
    }

    private String extractToken(String token) {
        if (!StringUtils.hasText(token)) {
            throw new ValidationException("The access token was not informed.");
        }
        if (token.contains(EMPTY_SPACE)) {
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        }
        return token;
    }

    private SecretKey generateSign() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private Date generExpiresAt() {
        return Date.from(
                LocalDateTime.now()
                        .plusHours(ONE_DAY_IN_HOURS)
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}

package com.moraes.microservices.statelessanyapi.api.service;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.moraes.microservices.statelessanyapi.api.dto.AuthUserResponse;
import com.moraes.microservices.statelessanyapi.api.exception.AuthenticationException;
import com.moraes.microservices.statelessanyapi.api.exception.ValidationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
@Service
public class JwtService {

    private static final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 1;

    @Value("${app.token.secret-key}")
    private String secretKey;

    @SneakyThrows
    public AuthUserResponse getAuthenticatedUser(String token) {
        var tokenClaims = getClaims(token);
        var userId = Long.valueOf(tokenClaims.get("id").toString());
        return new AuthUserResponse(userId, tokenClaims.get("username").toString());
    }

    public void validateAccessToken(String token) {

    }

    private Claims getClaims(String token) {
        var accessToken = extractToken(token);
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(generateSign())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (Exception e) {
            throw new AuthenticationException("Invalid token" + e.getMessage());
        }
    }

    private SecretKey generateSign() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
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
}

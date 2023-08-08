package com.moraes.microservices.statefulauthapi.api.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moraes.microservices.statefulauthapi.api.exception.AuthenticationException;
import com.moraes.microservices.statefulauthapi.api.exception.ValidationException;
import com.moraes.microservices.statefulauthapi.api.model.dto.TokenData;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenService {

    private static final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 1;
    private static final Long ONE_DAY_IN_SECONDS = 86400L;

    private final RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper objectMapper;

    public String createToken(String username) {
        final var accessToken = UUID.randomUUID().toString();
        final var data = new TokenData(username);
        final var jsonData = getJsonData(data);
        redisTemplate.opsForValue().set(accessToken, jsonData);
        redisTemplate.expireAt(accessToken, Instant.now().plusSeconds(ONE_DAY_IN_SECONDS));
        return accessToken;
    }

    public TokenData getTokenData(String token) {
        final var accessToken = extractToken(token);
        final var jsonString = getRedisTokenValue(accessToken);
        try {
            return objectMapper.readValue(jsonString, TokenData.class);
        } catch (Exception e) {
            throw new AuthenticationException("Error extracting the authenticated user: " + e.getMessage());
        }
    }

    public boolean validateAccessToken(String token) {
        return StringUtils.hasText(getRedisTokenValue(extractToken(token)));
    }

    public void deleteRedisToken(String token) {
        final var accessToken = extractToken(token);
        redisTemplate.delete(accessToken);
    }

    private String getRedisTokenValue(String token) {
        return redisTemplate.opsForValue().get(token);
    }

    private String getJsonData(Object payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            return "";
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
}

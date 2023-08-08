package com.moraes.microservices.statefulanyapi.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.moraes.microservices.statefulanyapi.api.client.ITokenClient;
import com.moraes.microservices.statefulanyapi.api.dto.AuthUserResponse;
import com.moraes.microservices.statefulanyapi.api.dto.TokenDTO;
import com.moraes.microservices.statefulanyapi.api.exception.AuthenticationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

    private final ITokenClient tokenClient;

    public void validateToken(String token) {
        try {
            log.info("Sending request for token validation {}", token);
            final ResponseEntity<TokenDTO> response = tokenClient.validateToken(token);
            log.info("Status {}", response.getStatusCode().toString());
            log.info("Token is valid {}", response.getBody().accessToken());
        } catch (Exception e) {
            log.warn("validateToken {}", e.getMessage());
            throw new AuthenticationException("Auth error: " + e.getMessage());
        }
    }

    public AuthUserResponse getAuthenticatedUser(String token) {
        try {
            log.info("Sending request for auth user: {}", token);
            final AuthUserResponse response = tokenClient.getAuthenticationUser(token);
            log.info("Auth user found: {}", response.toString());
            if (ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.id())) {
                throw new AuthenticationException("User is not found.");
            }
            return response;
        } catch (Exception e) {
            log.warn("getAuthenticatedUser {}", e.getMessage());
            throw new AuthenticationException("Auth to get authenticated user: " + e.getMessage());
        }
    }
}

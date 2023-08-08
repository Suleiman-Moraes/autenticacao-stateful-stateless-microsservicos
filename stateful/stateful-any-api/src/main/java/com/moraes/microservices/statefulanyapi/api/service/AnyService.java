package com.moraes.microservices.statefulanyapi.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.moraes.microservices.statefulanyapi.api.dto.AnyResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnyService {

    private final TokenService tokenService;

    public AnyResponse getData(String accessToken) {
        tokenService.validateToken(accessToken);
        final var authUser = tokenService.getAuthenticatedUser(accessToken);
        final HttpStatus ok = HttpStatus.OK;
        return new AnyResponse(ok.name(), ok.value(), authUser);
    }
}

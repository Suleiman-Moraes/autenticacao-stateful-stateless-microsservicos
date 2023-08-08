package com.moraes.microservices.statelessanyapi.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.moraes.microservices.statelessanyapi.api.dto.AnyResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnyService {

    private final JwtService jwtService;

    public AnyResponse getData(String accessToken) {
        jwtService.validateAccessToken(accessToken);
        final var authUser = jwtService.getAuthenticatedUser(accessToken);
        final var ok = HttpStatus.OK;
        return new AnyResponse(ok.name(), ok.value(), authUser);
    }
}

package com.moraes.microservices.statefulanyapi.api.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.moraes.microservices.statefulanyapi.api.dto.AuthUserResponse;
import com.moraes.microservices.statefulanyapi.api.dto.TokenDTO;

@HttpExchange("/api/auth")
public interface ITokenClient {

    @PostExchange(value = "token/validate")
    ResponseEntity<TokenDTO> validateToken(@RequestHeader String accessToken);

    @GetExchange(value = "user")
    AuthUserResponse getAuthenticationUser(@RequestHeader String accessToken);
}
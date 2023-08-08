package com.moraes.microservices.statelessauthapi.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moraes.microservices.statelessauthapi.api.model.dto.AuthRequest;
import com.moraes.microservices.statelessauthapi.api.model.dto.TokenDTO;
import com.moraes.microservices.statelessauthapi.api.service.AuthService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping(value = "login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping(value = "token/validate")
    public ResponseEntity<TokenDTO> validateToken(@RequestHeader String accessToken) {
        return ResponseEntity.ok(service.validateToken(accessToken));
    }
}

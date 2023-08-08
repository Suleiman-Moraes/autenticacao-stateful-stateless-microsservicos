package com.moraes.microservices.statefulauthapi.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moraes.microservices.statefulauthapi.api.model.dto.AuthRequest;
import com.moraes.microservices.statefulauthapi.api.model.dto.AuthUserResponse;
import com.moraes.microservices.statefulauthapi.api.model.dto.TokenDTO;
import com.moraes.microservices.statefulauthapi.api.service.AuthService;

import lombok.AllArgsConstructor;

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

    @PostMapping(value = "logout")
    public ResponseEntity<Void> logout(@RequestHeader String accessToken) {
        service.logout(accessToken);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "user")
    public ResponseEntity<AuthUserResponse> getAuthenticationUser(@RequestHeader String accessToken) {
        return ResponseEntity.ok(service.getAuthenticationUser(accessToken));
    }
}

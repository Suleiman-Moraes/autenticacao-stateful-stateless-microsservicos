package com.moraes.microservices.statelessauthapi.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.moraes.microservices.statelessauthapi.api.exception.ValidationException;
import com.moraes.microservices.statelessauthapi.api.model.dto.AuthRequest;
import com.moraes.microservices.statelessauthapi.api.model.dto.TokenDTO;
import com.moraes.microservices.statelessauthapi.api.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final IUserRepository userRepository;

    public TokenDTO login(AuthRequest authRequest) {
        var user = userRepository.findTopByUsername(authRequest.username())
                .orElseThrow(() -> new ValidationException("User not found!"));
        var accessToken = jwtService.createToken(user);
        validatePassword(authRequest.password(), user.getPassword());
        return new TokenDTO(accessToken);
    }

    public TokenDTO validateToken(String accessToken) {
        validateExistingToken(accessToken);
        jwtService.validateAccessToken(accessToken);
        return new TokenDTO(accessToken);
    }

    private void validateExistingToken(String accessToken) {
        if (!StringUtils.hasText(accessToken)) {
            throw new ValidationException("The access token must be informed!");
        }
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ValidationException("The password is incorrect!");
        }
    }
}

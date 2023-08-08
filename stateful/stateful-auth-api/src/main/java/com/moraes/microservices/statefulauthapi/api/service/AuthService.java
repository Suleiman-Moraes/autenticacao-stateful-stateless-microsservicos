package com.moraes.microservices.statefulauthapi.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.moraes.microservices.statefulauthapi.api.exception.AuthenticationException;
import com.moraes.microservices.statefulauthapi.api.exception.ValidationException;
import com.moraes.microservices.statefulauthapi.api.model.User;
import com.moraes.microservices.statefulauthapi.api.model.dto.AuthRequest;
import com.moraes.microservices.statefulauthapi.api.model.dto.AuthUserResponse;
import com.moraes.microservices.statefulauthapi.api.model.dto.TokenDTO;
import com.moraes.microservices.statefulauthapi.api.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public TokenDTO login(AuthRequest authRequest) {
        final var user = findByUsername(authRequest.username());
        var accessToken = tokenService.createToken(user.getUsername());
        validatePassword(authRequest.password(), user.getPassword());
        return new TokenDTO(accessToken);
    }

    public TokenDTO validateToken(String accessToken) {
        validateExistingToken(accessToken);
        if (tokenService.validateAccessToken(accessToken)) {
            return new TokenDTO(accessToken);
        }
        throw new AuthenticationException("Invalid token!");
    }

    public AuthUserResponse getAuthenticationUser(String accessToken) {
        final var tokenData = tokenService.getTokenData(accessToken);
        final var user = findByUsername(tokenData.username());
        return new AuthUserResponse(user.getId(), user.getUsername());
    }

    public void logout(String accessToken) {
        tokenService.deleteRedisToken(accessToken);
    }

    private User findByUsername(String username) {
        return userRepository.findTopByUsername(username)
                .orElseThrow(() -> new ValidationException("User not found!"));
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

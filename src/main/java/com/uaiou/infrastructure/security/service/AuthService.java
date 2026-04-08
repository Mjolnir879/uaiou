package com.uaiou.infrastructure.security.service;

import com.uaiou.infrastructure.persistence.entity.UserEntity;
import com.uaiou.infrastructure.persistence.repository.UserJpaRepository;
import com.uaiou.infrastructure.web.dto.request.LoginRequest;
import com.uaiou.infrastructure.web.dto.response.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    @Value("${token.access.duration}")
    private long accessTokenDuration;

    public AuthService(UserJpaRepository userJpaRepository,
                       PasswordEncoder passwordEncoder,
                       JwtEncoder jwtEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        UserEntity user = userJpaRepository.findByEmailAndActiveTrue(request.email())
                .orElseThrow(() -> new BadCredentialsException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Email ou senha inválidos");
        }

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("uaiou")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(accessTokenDuration))
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponse(accessToken, accessTokenDuration);
    }
}

package com.example.hexagonalarchitecture.user.application.service;

import com.example.hexagonalarchitecture.user.domain.port.in.RefreshTokenUseCase;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.RefreshTokenEntity;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.RefreshTokenJpaRepository;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.UsuarioEntity;
import com.example.hexagonalarchitecture.user.infraestructure.security.JwtService;
import com.example.hexagonalarchitecture.user.infraestructure.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RefreshTokenService implements RefreshTokenUseCase {

    private final RefreshTokenJpaRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenService(
            RefreshTokenJpaRepository refreshTokenRepository,
            JwtService jwtService
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    @Override
    public String refresh(String refreshToken) {
        RefreshTokenEntity tokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));

        if (tokenEntity.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expirado");
        }

        UsuarioEntity usuario = tokenEntity.getUsuario();

        return jwtService.generateToken(usuario.getEmail(), usuario.getNombre());
    }

}

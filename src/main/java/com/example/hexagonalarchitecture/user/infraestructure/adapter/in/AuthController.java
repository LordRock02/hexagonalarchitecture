package com.example.hexagonalarchitecture.user.infraestructure.adapter.in;

import com.example.hexagonalarchitecture.user.application.service.RefreshTokenService;
import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.in.LoginUsuarioUseCase;
import com.example.hexagonalarchitecture.user.domain.dto.AuthResponseDTO;
import com.example.hexagonalarchitecture.user.domain.dto.LoginRequestDTO;
import com.example.hexagonalarchitecture.user.domain.dto.RefreshRequestDTO;
import com.example.hexagonalarchitecture.user.domain.dto.RefreshResponseDTO;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.RefreshTokenEntity;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.RefreshTokenJpaRepository;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.SpringDataUsuarioRepository;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.UsuarioEntity;
import com.example.hexagonalarchitecture.user.infraestructure.security.JwtService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final LoginUsuarioUseCase loginUsuarioUseCase;
    private final JwtService jwtService;
    private final RefreshTokenJpaRepository refreshTokenRepository;
    private final SpringDataUsuarioRepository usuarioJpaRepository;
    private final RefreshTokenService refreshTokenService;

    public AuthController(
            LoginUsuarioUseCase loginUsuarioUseCase,
            JwtService jwtService,
            RefreshTokenJpaRepository refreshTokenRepository,
            SpringDataUsuarioRepository usuarioJpaRepository,
            RefreshTokenService refreshTokenService
    ) {
        this.loginUsuarioUseCase = loginUsuarioUseCase;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {

        Usuario usuario = loginUsuarioUseCase.login(
                request.email(),
                request.password()
        );

        UsuarioEntity usuarioEntity = usuarioJpaRepository
                .findByEmail(usuario.getEmail())
                .orElseThrow();

        String accessToken = jwtService.generateToken(usuarioEntity.getEmail(), usuarioEntity.getNombre());

        String refreshToken = UUID.randomUUID().toString();

        refreshTokenRepository.save(
                new RefreshTokenEntity(
                        null,
                        refreshToken,
                        usuarioEntity,
                        Instant.now().plus(7, ChronoUnit.DAYS)
                )
        );

        return new AuthResponseDTO(
                accessToken,
                refreshToken,
                usuario.getNombre()
        );
    }

    @PostMapping("/refresh")
    public RefreshResponseDTO refresh(@RequestBody RefreshRequestDTO request) {
        String newAccessToken = refreshTokenService.refresh(request.refreshToken());
        return new RefreshResponseDTO(newAccessToken);
    }


}



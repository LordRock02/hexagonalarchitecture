package com.example.hexagonalarchitecture.user.infraestructure.adapter.in;

import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.in.LoginUsuarioUseCase;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.RefreshTokenEntity;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.RefreshTokenJpaRepository;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.SpringDataUsuarioRepository;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.UsuarioEntity;
import com.example.hexagonalarchitecture.user.infraestructure.security.JwtService;
import com.example.hexagonalarchitecture.user.infraestructure.security.UserDetailsImpl;
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

    public AuthController(
            LoginUsuarioUseCase loginUsuarioUseCase,
            JwtService jwtService,
            RefreshTokenJpaRepository refreshTokenRepository,
            SpringDataUsuarioRepository usuarioJpaRepository
    ) {
        this.loginUsuarioUseCase = loginUsuarioUseCase;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        Usuario usuario = loginUsuarioUseCase.login(
                request.email(),
                request.password()
        );

        UsuarioEntity usuarioEntity = usuarioJpaRepository
                .findByEmail(usuario.getEmail())
                .orElseThrow();

        UserDetailsImpl userDetails = new UserDetailsImpl(usuarioEntity);

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = UUID.randomUUID().toString();

        refreshTokenRepository.save(
                new RefreshTokenEntity(
                        null,
                        refreshToken,
                        usuarioEntity,
                        Instant.now().plus(7, ChronoUnit.DAYS)
                )
        );

        return new AuthResponse(
                accessToken,
                refreshToken,
                usuario.getNombre()
        );
    }
}



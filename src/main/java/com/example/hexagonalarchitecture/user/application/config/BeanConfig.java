package com.example.hexagonalarchitecture.user.application.config;

import com.example.hexagonalarchitecture.user.application.service.LoginUsuarioService;
import com.example.hexagonalarchitecture.user.application.service.RegistrarUsuarioService;
import com.example.hexagonalarchitecture.user.domain.port.in.LoginUsuarioUseCase;
import com.example.hexagonalarchitecture.user.domain.port.in.RegistrarUsuarioUseCase;
import com.example.hexagonalarchitecture.user.domain.port.out.UsuarioRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean
    public RegistrarUsuarioUseCase registrarUsuarioUseCase(
            UsuarioRepositoryPort usuarioRepositoryPort,
            PasswordEncoder passwordEncoder
    ) {
        return new RegistrarUsuarioService(usuarioRepositoryPort, passwordEncoder);
    }

    @Bean
    public LoginUsuarioUseCase loginUsuarioUseCase(
            UsuarioRepositoryPort usuarioRepositoryPort,
            PasswordEncoder passwordEncoder
    ) {
        return new LoginUsuarioService(usuarioRepositoryPort, passwordEncoder);
    }
}

package com.example.hexagonalarchitecture.user.application.config;

import com.example.hexagonalarchitecture.user.application.service.RegistrarUsuarioService;
import com.example.hexagonalarchitecture.user.domain.port.out.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public RegistrarUsuarioService registrarUsuarioUseCase(
            UsuarioRepository usuarioRepository) {
        return new RegistrarUsuarioService(usuarioRepository);
    }
}

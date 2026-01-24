package com.example.hexagonalarchitecture.aplication;

import com.example.hexagonalarchitecture.user.application.config.BeanConfig;
import com.example.hexagonalarchitecture.user.application.service.RegistrarUsuarioService;
import com.example.hexagonalarchitecture.user.domain.port.in.RegistrarUsuarioUseCase;
import com.example.hexagonalarchitecture.user.domain.port.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BeanConfigTest {

    @Test
    void deberiaCrearBeanRegistrarUsuarioUseCase() {
        // Arrange
        BeanConfig beanConfig = new BeanConfig();

        UsuarioRepositoryPort usuarioRepositoryPort =
                mock(UsuarioRepositoryPort.class);

        PasswordEncoder passwordEncoder =
                mock(PasswordEncoder.class);

        // Act
        RegistrarUsuarioUseCase useCase =
                beanConfig.registrarUsuarioUseCase(
                        usuarioRepositoryPort,
                        passwordEncoder
                );

        // Assert
        assertNotNull(useCase);
        assertTrue(useCase instanceof RegistrarUsuarioService);
    }
}

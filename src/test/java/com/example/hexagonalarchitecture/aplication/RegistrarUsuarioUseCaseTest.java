package com.example.hexagonalarchitecture.aplication;

import com.example.hexagonalarchitecture.user.application.service.RegistrarUsuarioService;
import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrarUsuarioUseCaseTest {

    private UsuarioRepositoryPort usuarioRepositoryPort;
    private PasswordEncoder passwordEncoder;
    private RegistrarUsuarioService registrarUsuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepositoryPort = mock(UsuarioRepositoryPort.class);
        passwordEncoder = mock(PasswordEncoder.class);
        registrarUsuarioService = new RegistrarUsuarioService(
                usuarioRepositoryPort,
                passwordEncoder
        );
    }

    @Test
    void debeRegistrarUsuarioConPasswordCodificada() {
        // Arrange
        Usuario usuario = new Usuario(
                "Pablo",
                "pablo@email.com",
                "passwordPlano"
        );

        when(passwordEncoder.encode("passwordPlano"))
                .thenReturn("passwordCodificada");

        ArgumentCaptor<Usuario> usuarioCaptor =
                ArgumentCaptor.forClass(Usuario.class);

        // Act
        registrarUsuarioService.registrar(usuario);

        // Assert
        verify(passwordEncoder, times(1))
                .encode("passwordPlano");

        verify(usuarioRepositoryPort, times(1))
                .guardar(usuarioCaptor.capture());

        Usuario usuarioGuardado = usuarioCaptor.getValue();

        assertEquals("Pablo", usuarioGuardado.getNombre());
        assertEquals("pablo@email.com", usuarioGuardado.getEmail());
        assertEquals("passwordCodificada", usuarioGuardado.getPassword());
    }
}
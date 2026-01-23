package com.example.hexagonalarchitecture.aplication;


import com.example.hexagonalarchitecture.user.application.service.LoginUsuarioService;
import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.in.LoginUsuarioUseCase;
import com.example.hexagonalarchitecture.user.domain.port.out.UsuarioRepositoryPort;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginUsuarioUseCaseTest {
    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginUsuarioService loginUsuarioService;

    @Test
    void login_ShouldReturnUser_WhenCredentialsAreValid() {
        // 1. Arrange
        String email = "test@ejemplo.com";
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword123";

        // MOCKEAMOS la entidad Usuario para no inventar su constructor
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getPassword()).thenReturn(encodedPassword);

        // Simulamos el comportamiento de tus dependencias exactas
        when(usuarioRepositoryPort.buscarPorEmail(email)).thenReturn(Optional.of(usuarioMock));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // 2. Act
        Usuario result = loginUsuarioService.login(email, rawPassword);

        // 3. Assert
        assertNotNull(result);
        assertEquals(usuarioMock, result);
        verify(usuarioRepositoryPort).buscarPorEmail(email);
    }

    @Test
    void login_ShouldThrowException_WhenUserNotFound() {
        // 1. Arrange
        String email = "noexiste@ejemplo.com";
        String rawPassword = "123";

        when(usuarioRepositoryPort.buscarPorEmail(email)).thenReturn(Optional.empty());

        // 2. Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loginUsuarioService.login(email, rawPassword);
        });

        // Verificamos que el mensaje sea EXACTAMENTE el de tu código
        assertEquals("Credenciales inválidas", exception.getMessage());
    }

    @Test
    void login_ShouldThrowException_WhenPasswordDoesNotMatch() {
        // 1. Arrange
        String email = "test@ejemplo.com";
        String rawPassword = "passwordErronea";
        String encodedPassword = "realPassword";

        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getPassword()).thenReturn(encodedPassword);

        when(usuarioRepositoryPort.buscarPorEmail(email)).thenReturn(Optional.of(usuarioMock));
        // Simulamos que el encoder dice que NO coinciden
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // 2. Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loginUsuarioService.login(email, rawPassword);
        });

        assertEquals("Credenciales inválidas", exception.getMessage());
    }


}

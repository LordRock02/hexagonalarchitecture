package com.example.hexagonalarchitecture.aplication;

import com.example.hexagonalarchitecture.user.application.service.ObtenerUsuarioAutenticadoService;
import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObtenerUsuarioAutenticadoUseCaseTest {

    private UsuarioRepositoryPort usuarioRepositoryPort;
    private ObtenerUsuarioAutenticadoService obtenerUsuarioAutenticadoService;

    @BeforeEach
    void setUp() {
        usuarioRepositoryPort = mock(UsuarioRepositoryPort.class);
        obtenerUsuarioAutenticadoService =
                new ObtenerUsuarioAutenticadoService(usuarioRepositoryPort);
    }

    @Test
    void debeRetornarUsuarioCuandoExiste() {
        // Arrange
        String email = "pablo@email.com";
        Usuario usuario =
                new Usuario("Pablo", email, "password");

        when(usuarioRepositoryPort.buscarPorEmail(email))
                .thenReturn(Optional.of(usuario));

        // Act
        Usuario resultado =
                obtenerUsuarioAutenticadoService.obtenerUsuarioActual(email);

        // Assert
        assertNotNull(resultado);
        assertEquals("Pablo", resultado.getNombre());
        assertEquals(email, resultado.getEmail());

        verify(usuarioRepositoryPort, times(1))
                .buscarPorEmail(email);
    }

    @Test
    void debeLanzarExcepcionCuandoUsuarioNoExiste() {
        // Arrange
        String email = "noexiste@email.com";

        when(usuarioRepositoryPort.buscarPorEmail(email))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> obtenerUsuarioAutenticadoService.obtenerUsuarioActual(email)
        );

        assertEquals("Usuario no encontrado", exception.getMessage());

        verify(usuarioRepositoryPort, times(1))
                .buscarPorEmail(email);
    }
}

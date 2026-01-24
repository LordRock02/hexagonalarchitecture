package com.example.hexagonalarchitecture.aplication;

import com.example.hexagonalarchitecture.user.infraestructure.persistence.SpringDataUsuarioRepository;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.UsuarioEntity;
import com.example.hexagonalarchitecture.user.infraestructure.security.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    private SpringDataUsuarioRepository repository;
    private CustomUserDetailsService service;

    @BeforeEach
    void setUp() {
        repository = mock(SpringDataUsuarioRepository.class);
        service = new CustomUserDetailsService(repository);
    }

    @Test
    void deberiaRetornarUserDetailsCuandoUsuarioExiste() {
        // Arrange
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setEmail("pablo@email.com");
        usuario.setPassword("password-encriptado");

        when(repository.findByEmail("pablo@email.com"))
                .thenReturn(Optional.of(usuario));

        // Act
        UserDetails userDetails =
                service.loadUserByUsername("pablo@email.com");

        // Assert
        assertNotNull(userDetails);
        assertEquals("pablo@email.com", userDetails.getUsername());
        verify(repository, times(1))
                .findByEmail("pablo@email.com");
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        // Arrange
        when(repository.findByEmail("noexiste@email.com"))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername("noexiste@email.com")
        );

        verify(repository, times(1))
                .findByEmail("noexiste@email.com");
    }
}

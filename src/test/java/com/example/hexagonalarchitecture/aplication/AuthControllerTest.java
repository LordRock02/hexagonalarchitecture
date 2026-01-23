package com.example.hexagonalarchitecture.aplication;

import com.example.hexagonalarchitecture.user.application.service.RefreshTokenService;
import com.example.hexagonalarchitecture.user.domain.dto.LoginRequestDTO;
import com.example.hexagonalarchitecture.user.domain.dto.RefreshRequestDTO;
import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.in.LoginUsuarioUseCase;
import com.example.hexagonalarchitecture.user.infraestructure.adapter.in.AuthController;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.RefreshTokenEntity;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.RefreshTokenJpaRepository;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.SpringDataUsuarioRepository;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.UsuarioEntity;
import com.example.hexagonalarchitecture.user.infraestructure.security.JwtService;
import com.example.hexagonalarchitecture.user.infraestructure.security.CustomUserDetailsService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean private LoginUsuarioUseCase loginUsuarioUseCase;
    @MockitoBean private JwtService jwtService;
    @MockitoBean private RefreshTokenJpaRepository refreshTokenRepository;
    @MockitoBean private SpringDataUsuarioRepository usuarioJpaRepository;
    @MockitoBean private RefreshTokenService refreshTokenService;
    @MockitoBean private CustomUserDetailsService customUserDetailsService;

    @Test
    void login_ShouldReturn200_AndAuthResponse_WhenCredentialsAreValid() throws Exception {
        // Arrange
        String email = "juan@test.com";
        String password = "123";
        String nombre = "Juan Perez";
        String fakeToken = "fake-jwt-token";

        LoginRequestDTO request = new LoginRequestDTO(email, password);

        Usuario usuarioDomain = mock(Usuario.class);
        when(usuarioDomain.getEmail()).thenReturn(email);
        when(usuarioDomain.getNombre()).thenReturn(nombre);

        UsuarioEntity entityMock = new UsuarioEntity();
        entityMock.setId(1L);
        entityMock.setEmail(email);
        entityMock.setNombre(nombre);
        entityMock.setPassword("encodedPass");

        when(loginUsuarioUseCase.login(email, password)).thenReturn(usuarioDomain);
        when(usuarioJpaRepository.findByEmail(email)).thenReturn(Optional.of(entityMock));
        when(jwtService.generateToken(email, nombre)).thenReturn(fakeToken);

        when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenReturn(mock(RefreshTokenEntity.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(fakeToken))
                .andExpect(jsonPath("$.nombre").value(nombre))
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    void refresh_ShouldReturn200_AndNewToken_WhenRefreshTokenIsValid() throws Exception {
        // Arrange
        String refreshToken = "uuid-valido-123";
        String newAccessToken = "nuevo-token-generado";

        RefreshRequestDTO request = new RefreshRequestDTO(refreshToken);

        when(refreshTokenService.refresh(refreshToken)).thenReturn(newAccessToken);

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(newAccessToken));
    }
}
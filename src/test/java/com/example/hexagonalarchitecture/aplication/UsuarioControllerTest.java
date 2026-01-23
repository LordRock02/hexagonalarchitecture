package com.example.hexagonalarchitecture.aplication;

import com.example.hexagonalarchitecture.user.domain.dto.RegistrarUsuarioRequestDTO;
import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.in.ObtenerUsuarioAutenticadoUseCase;
import com.example.hexagonalarchitecture.user.domain.port.in.RegistrarUsuarioUseCase;
import com.example.hexagonalarchitecture.user.infraestructure.adapter.in.UsuarioController;
import com.example.hexagonalarchitecture.user.infraestructure.security.JwtAuthFilter;
import com.example.hexagonalarchitecture.user.infraestructure.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private RegistrarUsuarioUseCase registrarUsuarioUseCase;

    @Mock
    private ObtenerUsuarioAutenticadoUseCase obtenerUsuarioAutenticadoUseCase;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(usuarioController)
                .build();
    }

    @Test
    void registrarUsuario_debeRetornar201() throws Exception {
        RegistrarUsuarioRequestDTO request =
                new RegistrarUsuarioRequestDTO("Pablo", "pablo@mail.com", "1234");

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(registrarUsuarioUseCase).registrar(any(Usuario.class));
    }

    @Test
    void obtenerUsuarioAutenticado_debeRetornar200() throws Exception {
        Usuario usuario = new Usuario("Pablo", "pablo@mail.com", "1234");

        when(obtenerUsuarioAutenticadoUseCase.obtenerUsuarioActual("pablo@mail.com"))
                .thenReturn(usuario);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("pablo@mail.com", null);

        mockMvc.perform(get("/api/v1/usuarios/me")
                        .principal(authentication))
                .andExpect(status().isOk());
    }
}

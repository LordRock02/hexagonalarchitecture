package com.example.hexagonalarchitecture.user.infraestructure.adapter.in;

import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.in.ObtenerUsuarioAutenticadoUseCase;
import com.example.hexagonalarchitecture.user.domain.port.in.RegistrarUsuarioUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final ObtenerUsuarioAutenticadoUseCase obtenerUsuarioAutenticadoUseCase;


    public UsuarioController(RegistrarUsuarioUseCase registrarUsuarioUseCase, ObtenerUsuarioAutenticadoUseCase obtenerUsuarioAutenticadoUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.obtenerUsuarioAutenticadoUseCase = obtenerUsuarioAutenticadoUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrar(@RequestBody RegistrarUsuarioRequest request) {

        Usuario usuario = new Usuario(
                request.nombre(),
                request.email(),
                request.password()
        );

        registrarUsuarioUseCase.registrar(usuario);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> me(Authentication authentication) {

        String email = authentication.getName(); // ← sub del JWT

        Usuario usuario =
                obtenerUsuarioAutenticadoUseCase.obtenerUsuarioActual(email);

        return ResponseEntity.ok(
                new UsuarioResponse(
                        usuario.getNombre(),
                        usuario.getEmail()
                )
        );
    }
}


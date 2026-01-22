package com.example.hexagonalarchitecture.user.infraestructure.adapter.in;

import com.example.hexagonalarchitecture.user.domain.port.in.LoginUsuarioUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginUsuarioUseCase loginUsuarioUseCase;

    public AuthController(LoginUsuarioUseCase loginUsuarioUseCase) {
        this.loginUsuarioUseCase = loginUsuarioUseCase;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        String nombre = loginUsuarioUseCase.login(
                request.email(),
                request.password()
        );

        return new LoginResponse(nombre);
    }
}


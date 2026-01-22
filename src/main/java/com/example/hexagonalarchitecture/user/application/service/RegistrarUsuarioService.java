package com.example.hexagonalarchitecture.user.application.service;

import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.in.RegistrarUsuarioUseCase;
import com.example.hexagonalarchitecture.user.domain.port.out.UsuarioRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrarUsuarioService implements RegistrarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public RegistrarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort,
                                   PasswordEncoder passwordEncoder) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registrar(Usuario usuario) {

        Usuario usuarioSeguro = new Usuario(
                usuario.getNombre(),
                usuario.getEmail(),
                passwordEncoder.encode(usuario.getPassword())
        );

        usuarioRepositoryPort.guardar(usuarioSeguro);
    }
}


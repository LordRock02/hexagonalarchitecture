package com.example.hexagonalarchitecture.user.application.service;

import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.in.LoginUsuarioUseCase;
import com.example.hexagonalarchitecture.user.domain.port.out.UsuarioRepository;

public class LoginUsuarioService implements LoginUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public LoginUsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public String login(String email, String password) {

        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario.getNombre();
    }
}

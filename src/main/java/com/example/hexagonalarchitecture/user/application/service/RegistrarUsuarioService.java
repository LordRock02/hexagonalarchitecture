package com.example.hexagonalarchitecture.user.application.service;

import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.in.RegistrarUsuarioUseCase;
import com.example.hexagonalarchitecture.user.domain.port.out.UsuarioRepository;
import lombok.RequiredArgsConstructor;

public class RegistrarUsuarioService implements RegistrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public  RegistrarUsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void registrar(Usuario usuario) {
        usuarioRepository.guardar(usuario);
    }
}

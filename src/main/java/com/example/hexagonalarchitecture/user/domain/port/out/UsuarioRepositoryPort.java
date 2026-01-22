package com.example.hexagonalarchitecture.user.domain.port.out;

import com.example.hexagonalarchitecture.user.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {
    void guardar(Usuario usuario);
    Optional<Usuario> buscarPorEmail(String email);

}
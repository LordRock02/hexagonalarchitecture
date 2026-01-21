package com.example.hexagonalarchitecture.user.domain.port.out;

import com.example.hexagonalarchitecture.user.domain.model.Usuario;

public interface UsuarioRepository {
    void guardar(Usuario usuario);
}
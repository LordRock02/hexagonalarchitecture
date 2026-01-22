package com.example.hexagonalarchitecture.user.domain.port.in;

import com.example.hexagonalarchitecture.user.domain.model.Usuario;

public interface ObtenerUsuarioAutenticadoUseCase {
    Usuario obtenerUsuarioActual(String email);
}


package com.example.hexagonalarchitecture.user.application.service;

import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.in.ObtenerUsuarioAutenticadoUseCase;
import com.example.hexagonalarchitecture.user.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class ObtenerUsuarioAutenticadoService
        implements ObtenerUsuarioAutenticadoUseCase {

    private final UsuarioRepositoryPort usuarioRepository;

    public ObtenerUsuarioAutenticadoService(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario obtenerUsuarioActual(String email) {
        return usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}

package com.example.hexagonalarchitecture.user.infraestructure.adapter.out;

import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.out.UsuarioRepository;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.SpringDataUsuarioRepository;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRepositoryJpaAdapter implements UsuarioRepository {

    private final SpringDataUsuarioRepository repository;

    public UsuarioRepositoryJpaAdapter(SpringDataUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void guardar(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity(
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getPassword()
        );
        repository.save(entity);
    }
}


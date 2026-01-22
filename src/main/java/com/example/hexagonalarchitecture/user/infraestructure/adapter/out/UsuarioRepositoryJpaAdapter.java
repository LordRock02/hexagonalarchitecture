package com.example.hexagonalarchitecture.user.infraestructure.adapter.out;

import com.example.hexagonalarchitecture.user.domain.model.Usuario;
import com.example.hexagonalarchitecture.user.domain.port.out.UsuarioRepositoryPort;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.SpringDataUsuarioRepository;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioRepositoryJpaAdapter implements UsuarioRepositoryPort {

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

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .map(entity -> new Usuario(
                        entity.getNombre(),
                        entity.getEmail(),
                        entity.getPassword()
                ));
    }
}


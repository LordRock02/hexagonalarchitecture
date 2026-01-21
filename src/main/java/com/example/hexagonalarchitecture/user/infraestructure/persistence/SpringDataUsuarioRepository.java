package com.example.hexagonalarchitecture.user.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUsuarioRepository
        extends JpaRepository<UsuarioEntity, Long> {

    boolean existsByEmail(String email);
}


package com.example.hexagonalarchitecture.user.infraestructure.security;

import com.example.hexagonalarchitecture.user.infraestructure.persistence.SpringDataUsuarioRepository;
import com.example.hexagonalarchitecture.user.infraestructure.persistence.UsuarioEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SpringDataUsuarioRepository repository;

    public CustomUserDetailsService(SpringDataUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        UsuarioEntity usuario = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new UserDetailsImpl(usuario);
    }
}

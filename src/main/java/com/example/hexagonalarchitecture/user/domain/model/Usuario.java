package com.example.hexagonalarchitecture.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Usuario {

    private String nombre;
    private String email;
    private String password;

}


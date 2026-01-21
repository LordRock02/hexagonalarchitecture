package com.example.hexagonalarchitecture.user.domain.model;


public class Usuario {

    private String nombre;
    private String email;
    private String password;

    // Constructor
    public Usuario(String nombre, String email, String password){
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // Getter y Setter para nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter y Setter para email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter y Setter para password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


package com.example.hexagonalarchitecture.user.infraestructure.adapter.in;

public record AuthResponse(String accessToken,
                           String refreshToken,
                           String nombre) {}


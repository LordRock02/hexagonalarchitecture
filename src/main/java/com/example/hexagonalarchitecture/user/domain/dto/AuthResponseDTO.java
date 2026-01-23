package com.example.hexagonalarchitecture.user.domain.dto;

public record AuthResponseDTO(String accessToken,
                              String refreshToken,
                              String nombre) {}


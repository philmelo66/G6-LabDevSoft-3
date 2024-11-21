package com.grupo6.lab3.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private Long userId;
    private String role;
} 
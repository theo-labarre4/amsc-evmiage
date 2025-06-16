package com.example.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurDto {
    private String id;
    private String nom;
    private String carteAcces;
    private String pinCode;
    private boolean enLocation;
}

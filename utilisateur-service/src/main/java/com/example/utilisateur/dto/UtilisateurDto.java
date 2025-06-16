package com.example.utilisateur.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UtilisateurDto {
    private String id;
    private String nom;
    private String carteAcces;
    private String pinCode;
    private boolean enLocation;
}
package com.example.vehicule.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VehiculeDTO {

    public enum Etat {
        OPERATIONNEL_GARE,
        OPERATIONNEL_USAGE,
        MAINTENANCE
    }

    private String id;

    private Etat etat;

    private String marque;

    private String modele;

    private int nbPlaces;

    private int kilometrage;

    private int niveauCharge;

    private String position; // "latitude,longitude" format
}

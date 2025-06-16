package com.example.vehicule.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicule") // Nom de la collection dans MongoDB
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Vehicule {

    public enum Etat {
        OPERATIONNEL_GARE,
        OPERATIONNEL_USAGE,
        MAINTENANCE
    }

    @Id
    private String id; // MongoDB utilise généralement des identifiants de type String

    @Indexed
    private Etat etat;

    private String marque;

    private String modele;

    private int nbPlaces;

    private int kilometrage;

    private int niveauCharge;

    private String position; // "latitude,longitude" format
}

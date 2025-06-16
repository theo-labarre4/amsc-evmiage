package com.example.utilisateur.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "utilisateur") // Nom de la collection dans MongoDB
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class Utilisateur {

    @Id
    private String id; // Utilisation de String pour l'identifiant, adapté à MongoDB
    private String nom;
    private String carteAcces;
    private String pinCode;
    private boolean enLocation; // Indique si l'utilisateur est en location

}

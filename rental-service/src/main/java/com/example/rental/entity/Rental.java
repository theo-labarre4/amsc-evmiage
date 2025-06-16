package com.example.rental.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "rental") // Nom de la collection dans MongoDB
@Data // Génère getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder // Pour créer des instances en mode fluent
public class Rental {

    @Id
    private String id; // Identifiant unique de la location

    private String vehiculeId; // Identifiant du véhicule loué
    private String userId; // Identifiant de l'utilisateur qui loue le véhicule
    private String stationId; // Identifiant de la station de service où le véhicule est loué

    private String startTime; // Heure de début de la location
    private String endTime; // Heure de fin de la location

}

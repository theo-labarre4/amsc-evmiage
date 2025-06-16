package com.example.stationservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "station") // Nom de la collection dans MongoDB
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class StationService {

    @Id
    private String id; // Utilisation de Long pour l'identifiant, adapté à MongoDB

    private String location; // Localisation de la station

    private int placeTotale; // Nombre total de places dans la station

    private int placeOccupee; // Nombre de places occupées dans la station


}

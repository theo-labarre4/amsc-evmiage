package com.example.stationservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

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

    private double latitude; // GPS latitude coordinate

    private double longitude; // GPS longitude coordinate

    private int placeTotale; // Nombre total de places dans la station

    private List<String> vehicleIds; // Liste des identifiants de véhicules associés à la station


    public boolean canAddVehicle() {
        return this.vehicleIds.size() < this.placeTotale;
    }
}

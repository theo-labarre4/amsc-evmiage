package com.example.stationservice.repository;

import com.example.stationservice.entity.StationService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends MongoRepository<StationService, String> {
    // Vous pouvez ajouter des méthodes de requête personnalisées ici si nécessaire
    // Par exemple, pour trouver des stations par localisation ou par nombre de places
    // List<StationService> findByLocation(String location);
    // List<StationService> findByPlaceTotale(int placeTotale);
}

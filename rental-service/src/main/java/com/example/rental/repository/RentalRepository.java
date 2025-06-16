package com.example.rental.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.rental.entity.Rental;

public interface RentalRepository extends MongoRepository<Rental, String> {
    // Vous pouvez ajouter des méthodes de requête personnalisées ici si nécessaire
    // Par exemple, pour trouver des locations par utilisateur ou par véhicule
    // List<Rental> findByUtilisateurId(String utilisateurId);
    // List<Rental> findByVehiculeId(String vehiculeId);
}

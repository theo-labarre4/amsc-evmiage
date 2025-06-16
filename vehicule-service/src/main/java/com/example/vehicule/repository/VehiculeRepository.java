package com.example.vehicule.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.vehicule.entity.Vehicule;

public interface VehiculeRepository extends MongoRepository<Vehicule, String> {
    // Vous pouvez ajouter des méthodes de requête personnalisées ici si nécessaire
    // Par exemple, pour trouver des véhicules par état ou par marque
    // List<Vehicule> findByEtat(Vehicule.Etat etat);
    // List<Vehicule> findByMarque(String marque);

}

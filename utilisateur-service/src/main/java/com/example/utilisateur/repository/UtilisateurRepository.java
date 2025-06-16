package com.example.utilisateur.repository;

import com.example.utilisateur.entity.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {
    // Vous pouvez ajouter des méthodes de requête personnalisées ici si nécessaire
    // Par exemple, pour trouver des utilisateurs par nom ou par email
    // List<Utilisateur> findByNom(String nom);
    // Utilisateur findByEmail(String email);
}

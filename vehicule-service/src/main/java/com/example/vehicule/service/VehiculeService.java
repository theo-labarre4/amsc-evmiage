package com.example.vehicule.service;

import com.example.vehicule.entity.Vehicule;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.example.vehicule.dto.VehiculeDTO;
import com.example.vehicule.repository.VehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehiculeService {
    private final VehiculeRepository vehiculeRepository;

    public VehiculeDTO creerVehicule(VehiculeDTO dto) {
        Vehicule vehicule = Vehicule.builder()
                .id(dto.getId())
                .etat(Vehicule.Etat.valueOf(dto.getEtat().name()))
                .marque(dto.getMarque())
                .modele(dto.getModele())
                .nbPlaces(dto.getNbPlaces())
                .kilometrage(dto.getKilometrage())
                .niveauCharge(dto.getNiveauCharge())
                .position(dto.getPosition())
                .build();

        Vehicule saved = vehiculeRepository.save(vehicule);
        log.info("Véhicule {} créé", saved.getId());

        return VehiculeDTO.builder()
                .id(saved.getId())
                .etat(VehiculeDTO.Etat.valueOf(saved.getEtat().name()))
                .marque(saved.getMarque())
                .modele(saved.getModele())
                .nbPlaces(saved.getNbPlaces())
                .kilometrage(saved.getKilometrage())
                .niveauCharge(saved.getNiveauCharge())
                .position(saved.getPosition())
                .build();
    }

    public List<VehiculeDTO> obtenirTousLesVehicules() {
        return vehiculeRepository.findAll()
                .stream()
                .map(vehicule -> VehiculeDTO.builder()
                        .id(vehicule.getId())
                        .etat(VehiculeDTO.Etat.valueOf(vehicule.getEtat().name()))
                        .marque(vehicule.getMarque())
                        .modele(vehicule.getModele())
                        .nbPlaces(vehicule.getNbPlaces())
                        .kilometrage(vehicule.getKilometrage())
                        .niveauCharge(vehicule.getNiveauCharge())
                        .position(vehicule.getPosition())
                        .build())
                .toList();
    }

    public VehiculeDTO obtenirVehiculeParId(String id) {
        return vehiculeRepository.findById(id)
                .map(vehicule -> VehiculeDTO.builder()
                        .id(vehicule.getId())
                        .etat(VehiculeDTO.Etat.valueOf(vehicule.getEtat().name()))
                        .marque(vehicule.getMarque())
                        .modele(vehicule.getModele())
                        .nbPlaces(vehicule.getNbPlaces())
                        .kilometrage(vehicule.getKilometrage())
                        .niveauCharge(vehicule.getNiveauCharge())
                        .position(vehicule.getPosition())
                        .build())
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));
    }

    public void supprimerVehicule(String id) {
        vehiculeRepository.deleteById(id);
        log.info("Véhicule {} supprimé", id);
    }

    public VehiculeDTO mettreAJourVehicule(String id, VehiculeDTO dto) {
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));

        vehicule.setEtat(Vehicule.Etat.valueOf(dto.getEtat().name()));
        vehicule.setMarque(dto.getMarque());
        vehicule.setModele(dto.getModele());
        vehicule.setNbPlaces(dto.getNbPlaces());
        vehicule.setKilometrage(dto.getKilometrage());
        vehicule.setNiveauCharge(dto.getNiveauCharge());
        vehicule.setPosition(dto.getPosition());

        Vehicule updated = vehiculeRepository.save(vehicule);
        log.info("Véhicule {} mis à jour", updated.getId());

        return VehiculeDTO.builder()
                .id(updated.getId())
                .etat(VehiculeDTO.Etat.valueOf(updated.getEtat().name()))
                .marque(updated.getMarque())
                .modele(updated.getModele())
                .nbPlaces(updated.getNbPlaces())
                .kilometrage(updated.getKilometrage())
                .niveauCharge(updated.getNiveauCharge())
                .position(updated.getPosition())
                .build();
    }

}
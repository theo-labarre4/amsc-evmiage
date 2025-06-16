package com.example.stationservice.service;

import com.example.stationservice.dto.StationServiceDto;
import com.example.stationservice.entity.StationService;
import com.example.stationservice.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StationServiceServ {

    @Autowired
    private StationRepository stationRepository;

    // Récupère toutes les stations et les convertit en DTO
    public List<StationServiceDto> findAll() {
        return stationRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Recherche une station par son id et la convertit en DTO si trouvée
    public Optional<StationServiceDto> findById(String id) {
        return stationRepository.findById(id)
                .map(this::toDto);
    }

    // Sauvegarde une station à partir d’un DTO et retourne le DTO sauvegardé
    public StationServiceDto save(StationServiceDto dto) {
        if(dto.getPlaceOccupee() >= dto.getPlaceTotale()) {
            throw new IllegalArgumentException("Le nombre de places occupées ne peut pas excéder le nombre total de places.");
        }
        StationService entity = toEntity(dto);
        StationService saved = stationRepository.save(entity);
        return toDto(saved);
    }

    // Supprime une station par son id
    public void deleteById(String id) {
        stationRepository.deleteById(id);
    }

    // Convertit une entité en DTO
    private StationServiceDto toDto(StationService entity) {
        return StationServiceDto.builder()
                .id(entity.getId())
                .location(entity.getLocation())
                .placeTotale(entity.getPlaceTotale())
                .placeOccupee(entity.getPlaceOccupee())
                .build();
    }

    // Convertit un DTO en entité
    private StationService toEntity(StationServiceDto dto) {
        return StationService.builder()
                .id(dto.getId())
                .location(dto.getLocation())
                .placeTotale(dto.getPlaceTotale())
                .placeOccupee(dto.getPlaceOccupee())
                .build();
    }
}
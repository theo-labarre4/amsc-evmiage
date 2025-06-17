package com.example.stationservice.service;

import com.example.stationservice.dto.StationServiceDto;
import com.example.stationservice.dto.ErrorResponse;
import com.example.stationservice.entity.StationService;
import com.example.stationservice.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Service
public class StationServiceServ {

    private static final Logger logger = LoggerFactory.getLogger(StationServiceServ.class);

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
    public ResponseEntity<?> save(StationServiceDto dto) {
        if(dto.getVehicleIds().size() > dto.getPlaceTotale()) {
            logger.error("Cannot create station: vehicle count ({}) exceeds total capacity ({})", 
                        dto.getVehicleIds().size(), dto.getPlaceTotale());
            ErrorResponse errorResponse = ErrorResponse.builder()
                .error("CAPACITY_EXCEEDED")
                .message("Vehicle count (" + dto.getVehicleIds().size() + ") exceeds station capacity (" + dto.getPlaceTotale() + ")")
                .status(400)
                .timestamp(LocalDateTime.now().toString())
                .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        // Check for duplicate vehicle IDs
        Set<String> uniqueVehicleIds = new HashSet<>(dto.getVehicleIds());
        if (uniqueVehicleIds.size() != dto.getVehicleIds().size()) {
            logger.error("Cannot create station: duplicate vehicle IDs found");
            ErrorResponse errorResponse = ErrorResponse.builder()
                .error("DUPLICATE_VEHICLE_IDS")
                .message("Duplicate vehicle IDs are not allowed in the same station")
                .status(400)
                .timestamp(LocalDateTime.now().toString())
                .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        StationService entity = toEntity(dto);
        StationService saved = stationRepository.save(entity);
        return ResponseEntity.ok(toDto(saved));
    }

    // Supprime une station par son id
    public void deleteById(String id) {
        stationRepository.deleteById(id);
    }

    // Met à jour la liste des IDs de véhicules pour une station donnée
    public ResponseEntity<?> updateVehicleIds(String id, List<String> vehicleIds) {
        Optional<StationServiceDto> stationOpt = findById(id);
        if (stationOpt.isEmpty()) {
            logger.error("Cannot update vehicle IDs: Station with ID '{}' not found", id);
            ErrorResponse errorResponse = ErrorResponse.builder()
                .error("STATION_NOT_FOUND")
                .message("Station with ID '" + id + "' not found")
                .status(404)
                .timestamp(LocalDateTime.now().toString())
                .build();
            return ResponseEntity.status(404).body(errorResponse);
        }
        
        StationServiceDto station = stationOpt.get();
        if (vehicleIds.size() > station.getPlaceTotale()) {
            logger.error("Cannot update vehicle IDs for station '{}': vehicle count ({}) exceeds total capacity ({})", 
                        id, vehicleIds.size(), station.getPlaceTotale());
            ErrorResponse errorResponse = ErrorResponse.builder()
                .error("CAPACITY_EXCEEDED")
                .message("Vehicle count (" + vehicleIds.size() + ") exceeds station capacity (" + station.getPlaceTotale() + ")")
                .status(400)
                .timestamp(LocalDateTime.now().toString())
                .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        // Check for duplicate vehicle IDs
        Set<String> uniqueVehicleIds = new HashSet<>(vehicleIds);
        if (uniqueVehicleIds.size() != vehicleIds.size()) {
            logger.error("Cannot update vehicle IDs for station '{}': duplicate vehicle IDs found", id);
            ErrorResponse errorResponse = ErrorResponse.builder()
                .error("DUPLICATE_VEHICLE_IDS")
                .message("Duplicate vehicle IDs are not allowed in the same station")
                .status(400)
                .timestamp(LocalDateTime.now().toString())
                .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        station.setVehicleIds(vehicleIds);
        StationService entity = toEntity(station);
        StationService saved = stationRepository.save(entity);
        return ResponseEntity.ok(toDto(saved));
    }

    // Ajoute un véhicule à la liste des véhicules d'une station
    public ResponseEntity<?> addVehicle(String stationId, String vehicleId) {
        Optional<StationServiceDto> stationOpt = findById(stationId);
        if (stationOpt.isEmpty()) {
            logger.error("Cannot add vehicle '{}': Station with ID '{}' not found", vehicleId, stationId);
            ErrorResponse errorResponse = ErrorResponse.builder()
                .error("STATION_NOT_FOUND")
                .message("Station with ID '" + stationId + "' not found")
                .status(404)
                .timestamp(LocalDateTime.now().toString())
                .build();
            return ResponseEntity.status(404).body(errorResponse);
        }
        
        StationServiceDto station = stationOpt.get();
        List<String> vehicleIds = station.getVehicleIds();
        
        // Check if vehicle already exists in station
        if (vehicleIds.contains(vehicleId)) {
            logger.error("Cannot add vehicle '{}' to station '{}': vehicle already exists in station", vehicleId, stationId);
            ErrorResponse errorResponse = ErrorResponse.builder()
                .error("VEHICLE_ALREADY_EXISTS")
                .message("Vehicle '" + vehicleId + "' already exists in station '" + stationId + "'")
                .status(400)
                .timestamp(LocalDateTime.now().toString())
                .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        if(vehicleIds.size() >= station.getPlaceTotale()) {
            logger.error("Cannot add vehicle '{}' to station '{}': station is full ({}/{})", 
                        vehicleId, stationId, vehicleIds.size(), station.getPlaceTotale());
            ErrorResponse errorResponse = ErrorResponse.builder()
                .error("STATION_FULL")
                .message("Station is full (" + vehicleIds.size() + "/" + station.getPlaceTotale() + ")")
                .status(400)
                .timestamp(LocalDateTime.now().toString())
                .build();
            return ResponseEntity.badRequest().body(errorResponse);
        } else {
            vehicleIds.add(vehicleId);
            station.setVehicleIds(vehicleIds);
            StationService entity = toEntity(station);
            StationService saved = stationRepository.save(entity);
            return ResponseEntity.ok(toDto(saved));
        }
    }

    // Supprime un véhicule de la liste des véhicules d'une station
    public ResponseEntity<?> removeVehicle(String stationId, String vehicleId) {
        Optional<StationServiceDto> stationOpt = findById(stationId);
        if (stationOpt.isEmpty()) {
            logger.error("Cannot remove vehicle '{}': Station with ID '{}' not found", vehicleId, stationId);
            ErrorResponse errorResponse = ErrorResponse.builder()
                .error("STATION_NOT_FOUND")
                .message("Station with ID '" + stationId + "' not found")
                .status(404)
                .timestamp(LocalDateTime.now().toString())
                .build();
            return ResponseEntity.status(404).body(errorResponse);
        }
        
        StationServiceDto station = stationOpt.get();
        List<String> vehicleIds = station.getVehicleIds();
        if (!vehicleIds.remove(vehicleId)) {
            logger.error("Cannot remove vehicle '{}' from station '{}': vehicle not found in station", 
                        vehicleId, stationId);
            ErrorResponse errorResponse = ErrorResponse.builder()
                .error("VEHICLE_NOT_FOUND")
                .message("Vehicle '" + vehicleId + "' not found in station '" + stationId + "'")
                .status(400)
                .timestamp(LocalDateTime.now().toString())
                .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
        station.setVehicleIds(vehicleIds);
        StationService entity = toEntity(station);
        StationService saved = stationRepository.save(entity);
        return ResponseEntity.ok(toDto(saved));
    }

    // Find nearby stations based on GPS coordinates
    public List<StationServiceDto> findNearbyStations(String stationId, int limit) {
        StationServiceDto currentStation = findById(stationId)
            .orElseThrow(() -> new IllegalArgumentException("Station not found"));
        
        return stationRepository.findAll()
            .stream()
            .filter(station -> !station.getId().equals(stationId))
            .map(this::toDto)
            .filter(station -> station.getVehicleIds().size() < station.getPlaceTotale()) // Only stations with available space
            .filter(station -> station.getPlaceTotale() > 0) // Ensure station has capacity
            .sorted(Comparator.comparingDouble(station -> 
                calculateDistance(currentStation.getLatitude(), currentStation.getLongitude(),
                               station.getLatitude(), station.getLongitude())))
            .limit(limit)
            .collect(Collectors.toList());
    }

    // Calculate distance between two GPS coordinates using Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c; // Distance in km
    }

    // Convertit une entité StationService en DTO
    private StationServiceDto toDto(StationService entity) {
        StationServiceDto dto = new StationServiceDto();
        dto.setId(entity.getId());
        dto.setLocation(entity.getLocation());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setPlaceTotale(entity.getPlaceTotale());
        dto.setVehicleIds(entity.getVehicleIds());
        return dto;
    }

    // Convertit un DTO StationServiceDto en entité
    private StationService toEntity(StationServiceDto dto) {
        StationService entity = new StationService();
        entity.setId(dto.getId());
        entity.setLocation(dto.getLocation());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setPlaceTotale(dto.getPlaceTotale());
        entity.setVehicleIds(dto.getVehicleIds());
        return entity;
    }
}
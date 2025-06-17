package com.example.stationservice.controller;

import com.example.stationservice.dto.StationServiceDto;
import com.example.stationservice.dto.ErrorResponse;
import com.example.stationservice.dto.NearbyStationResponse;
import com.example.stationservice.service.StationServiceServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stations")
public class StationServiceController {

    @Autowired
    private StationServiceServ stationServiceServ;

    // Récupérer toutes les stations
    @GetMapping
    public List<StationServiceDto> getAllStations() {
        return stationServiceServ.findAll();
    }

    // Récupérer une station par son id
    @GetMapping("/{id}")
    public ResponseEntity<StationServiceDto> getStationById(@PathVariable String id) {
        Optional<StationServiceDto> station = stationServiceServ.findById(id);
        return station.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer une nouvelle station
    @PostMapping
    public ResponseEntity<?> createStation(@RequestBody StationServiceDto dto) {
        return stationServiceServ.save(dto);
    }

    // Supprimer une station par son id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable String id) {
        stationServiceServ.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Mettre à jour la liste des identifiants de véhicules
    @PutMapping("/{id}/vehicles")
    public ResponseEntity<?> updateVehicleIds(
        @PathVariable String id, 
        @RequestBody List<String> vehicleIds
    ) {
        return stationServiceServ.updateVehicleIds(id, vehicleIds);
    }

    // Ajouter un véhicule à la station
    @PostMapping("/{id}/vehicles")
    public ResponseEntity<?> addVehicleToStation(
        @PathVariable String id, 
        @RequestBody String vehicleId
    ) {
        ResponseEntity<?> result = stationServiceServ.addVehicle(id, vehicleId);
        
        if (result.getStatusCode().is4xxClientError() && result.getBody() instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) result.getBody();
            if ("STATION_FULL".equals(errorResponse.getError())) {
                // Station is full, return nearby stations
                List<StationServiceDto> nearbyStations = stationServiceServ.findNearbyStations(id, 3);
                NearbyStationResponse response = NearbyStationResponse.builder()
                    .message("Station is full. Here are 3 nearby stations with available spaces:")
                    .nearbyStations(nearbyStations)
                    .build();
                return ResponseEntity.badRequest().body(response);
            }
        }
        
        return result;
    }

    // Supprimer un véhicule de la liste des identifiants de véhicules
    @DeleteMapping("/{id}/vehicles/{vehicleId}")
    public ResponseEntity<?> removeVehicleFromStation(
        @PathVariable String id, 
        @PathVariable String vehicleId
    ) {
        return stationServiceServ.removeVehicle(id, vehicleId);
    }
}
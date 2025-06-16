package com.example.stationservice.controller;

import com.example.stationservice.dto.StationServiceDto;
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
    public ResponseEntity<StationServiceDto> createStation(@RequestBody StationServiceDto dto) {
        StationServiceDto created = stationServiceServ.save(dto);
        return ResponseEntity.ok(created);
    }

    // Supprimer une station par son id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable String id) {
        stationServiceServ.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
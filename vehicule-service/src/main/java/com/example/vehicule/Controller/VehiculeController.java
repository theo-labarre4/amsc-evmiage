package com.example.vehicule.Controller;

import com.example.vehicule.dto.VehiculeDTO;
import com.example.vehicule.service.VehiculeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @PostMapping
    public ResponseEntity<VehiculeDTO> creerVehicule(@RequestBody VehiculeDTO dto) {
        VehiculeDTO savedVehicule = vehiculeService.creerVehicule(dto);
        return ResponseEntity.status(201).body(savedVehicule);
    }

    @GetMapping
    public ResponseEntity<List<VehiculeDTO>> obtenirTousLesVehicules() {
        List<VehiculeDTO> vehicules = vehiculeService.obtenirTousLesVehicules();
        return ResponseEntity.ok(vehicules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculeDTO> obtenirVehiculeParId(@PathVariable String id) {
        VehiculeDTO vehicule = vehiculeService.obtenirVehiculeParId(id);
        return ResponseEntity.ok(vehicule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerVehicule(@PathVariable String id) {
        vehiculeService.supprimerVehicule(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculeDTO> mettreAJourVehicule(@PathVariable String id, @RequestBody VehiculeDTO dto) {
        VehiculeDTO updatedVehicule = vehiculeService.mettreAJourVehicule(id, dto);
        return ResponseEntity.ok(updatedVehicule);
    }
}
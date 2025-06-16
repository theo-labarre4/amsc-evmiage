package com.example.utilisateur.controller;

import com.example.utilisateur.dto.UtilisateurDto;
import com.example.utilisateur.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @PostMapping
    public ResponseEntity<UtilisateurDto> creerUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        UtilisateurDto created = utilisateurService.creerUtilisateur(utilisateurDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<UtilisateurDto>> listerUtilisateurs() {
        List<UtilisateurDto> utilisateurs = utilisateurService.listerUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDto> trouverParId(@PathVariable String id) {
        Optional<UtilisateurDto> utilisateur = utilisateurService.trouverParId(id);
        return utilisateur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDto> mettreAJourUtilisateur(@PathVariable String id, @RequestBody UtilisateurDto utilisateurDto) {
        Optional<UtilisateurDto> updated = utilisateurService.mettreAJourUtilisateur(id, utilisateurDto);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable String id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}
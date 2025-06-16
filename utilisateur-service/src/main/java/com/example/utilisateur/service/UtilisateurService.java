package com.example.utilisateur.service;

import com.example.utilisateur.dto.UtilisateurDto;
import com.example.utilisateur.entity.Utilisateur;
import com.example.utilisateur.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurDto creerUtilisateur(UtilisateurDto dto) {
        Utilisateur utilisateur = toEntity(dto);
        Utilisateur saved = utilisateurRepository.save(utilisateur);
        return toDto(saved);
    }

    public List<UtilisateurDto> listerUtilisateurs() {
        return utilisateurRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UtilisateurDto> trouverParId(String id) {
        return utilisateurRepository.findById(id)
                .map(this::toDto);
    }

    public Optional<UtilisateurDto> mettreAJourUtilisateur(String id, UtilisateurDto dto) {
        return utilisateurRepository.findById(id)
                .map(existing -> {
                    existing.setNom(dto.getNom());
                    existing.setCarteAcces(dto.getCarteAcces());
                    existing.setPinCode(dto.getPinCode());
                    existing.setEnLocation(dto.isEnLocation());
                    Utilisateur updated = utilisateurRepository.save(existing);
                    return toDto(updated);
                });
    }

    public void supprimerUtilisateur(String id) {
        utilisateurRepository.deleteById(id);
    }

    private UtilisateurDto toDto(Utilisateur utilisateur) {
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .carteAcces(utilisateur.getCarteAcces())
                .pinCode(utilisateur.getPinCode())
                .enLocation(utilisateur.isEnLocation())
                .build();
    }

    private Utilisateur toEntity(UtilisateurDto dto) {
        return Utilisateur.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .carteAcces(dto.getCarteAcces())
                .pinCode(dto.getPinCode())
                .enLocation(dto.isEnLocation())
                .build();
    }
}
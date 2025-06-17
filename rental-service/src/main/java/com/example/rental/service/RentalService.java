package com.example.rental.service;

import com.example.rental.dto.RentalDto;
import com.example.rental.entity.Rental;
import com.example.rental.feign.UtilisateurClient;
import com.example.rental.feign.VehiculeClient;
import com.example.rental.feign.StationClient;
import com.example.rental.repository.RentalRepository;
import com.example.rental.dto.UtilisateurDto;
import com.example.rental.dto.VehiculeDto;
import com.example.rental.dto.StationDto;
import com.example.rental.dto.NearbyStationResponse;
import com.example.rental.exception.StationFullException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final UtilisateurClient utilisateurClient;
    private final VehiculeClient vehiculeClient;
    private final StationClient stationClient;
    private final RentalRepository rentalRepository;

    public Rental startRental(RentalDto request) {
        // 1. Vérifier que l'utilisateur est disponible
        UtilisateurDto utilisateur = utilisateurClient.trouverParId(request.getUserId());
        if (utilisateur.isEnLocation()) {
            throw new IllegalStateException("L'utilisateur est déjà en location.");
        }

        // 2. Vérifier que le véhicule est disponible
        VehiculeDto vehicule = vehiculeClient.getVehicule(request.getVehiculeId());
        if (vehicule.getEtat() != VehiculeDto.Etat.OPERATIONNEL_GARE) {
            throw new IllegalStateException("Le véhicule n'est pas disponible pour une location.");
        }

        // 3. Essayer d'ajouter le véhicule à la station (qui va le retirer de fait)
        try {
            ResponseEntity<?> response = stationClient.addVehicleToStation(request.getStationId(), request.getVehiculeId());
            
            if (response.getStatusCode().is4xxClientError() && response.getBody() instanceof NearbyStationResponse) {
                NearbyStationResponse nearbyResponse = (NearbyStationResponse) response.getBody();
                throw new StationFullException(nearbyResponse.getMessage(), nearbyResponse.getNearbyStations());
            }
        } catch (Exception e) {
            if (e instanceof StationFullException) {
                throw e;
            }
            throw new IllegalStateException("Erreur lors de la réservation du véhicule à la station.");
        }

        // 4. Mettre à jour l'utilisateur : enLocation = true
        utilisateur.setEnLocation(true);
        utilisateurClient.mettreAJourUtilisateur(utilisateur.getId(), utilisateur);

        // 5. Mettre à jour le véhicule : OPERATIONNEL_USAGE
        vehicule.setEtat(VehiculeDto.Etat.OPERATIONNEL_USAGE);
        vehiculeClient.mettreAJourVehicule(vehicule.getId(), vehicule);

        // 6. Enregistrer la location
        Rental rental = Rental.builder()
                .userId(utilisateur.getId())
                .vehiculeId(vehicule.getId())
                .stationId(request.getStationId())
                .startTime(LocalDateTime.now().toString())
                .build();

        return rentalRepository.save(rental);
    }

    public Rental endRental(String rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Location non trouvée"));

        // 1. Récupérer l'utilisateur
        UtilisateurDto utilisateur = utilisateurClient.trouverParId(rental.getUserId());
        if (!utilisateur.isEnLocation()) {
            throw new IllegalStateException("L'utilisateur n'est pas en location.");
        }

        // 2. Récupérer le véhicule
        VehiculeDto vehicule = vehiculeClient.getVehicule(rental.getVehiculeId());
        if (vehicule.getEtat() != VehiculeDto.Etat.OPERATIONNEL_USAGE) {
            throw new IllegalStateException("Le véhicule n'est pas en usage.");
        }

        // 3. Récupérer la station
        StationDto station = stationClient.getStation(rental.getStationId());

        // 4. Mettre à jour l'utilisateur : enLocation = false
        utilisateur.setEnLocation(false);
        utilisateurClient.mettreAJourUtilisateur(utilisateur.getId(), utilisateur);

        // 5. Mettre à jour le véhicule : OPERATIONNEL_GARE
        vehicule.setEtat(VehiculeDto.Etat.OPERATIONNEL_GARE);
        vehiculeClient.mettreAJourVehicule(vehicule.getId(), vehicule);

        // 6. Mettre à jour la station : placeOccupee++
        station.setPlaceOccupee(station.getPlaceOccupee() + 1);
        stationClient.mettreAJourStation(station.getId(), station);

        // 7. Mettre à jour la location avec l'heure de fin
        rental.setEndTime(LocalDateTime.now().toString());

        return rentalRepository.save(rental);
    }

}

package com.example.rental.controller;

import com.example.rental.dto.RentalDto;
import com.example.rental.dto.NearbyStationResponse;
import com.example.rental.entity.Rental;
import com.example.rental.exception.StationFullException;
import com.example.rental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/start")
    public ResponseEntity<?> startRental(@RequestBody RentalDto request) {
        try {
            Rental rental = rentalService.startRental(request);
            return ResponseEntity.ok(rental);
        } catch (StationFullException e) {
            NearbyStationResponse response = NearbyStationResponse.builder()
                    .message(e.getMessage())
                    .nearbyStations(e.getNearbyStations())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/end/{rentalId}")
    public ResponseEntity<Rental> endRental(@PathVariable String rentalId) {
        Rental rental = rentalService.endRental(rentalId);
        return ResponseEntity.ok(rental);
    }
}
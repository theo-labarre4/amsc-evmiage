package com.example.rental.exception;

import com.example.rental.dto.StationDto;
import lombok.Getter;

import java.util.List;

@Getter
public class StationFullException extends RuntimeException {
    private final List<StationDto> nearbyStations;
    
    public StationFullException(String message, List<StationDto> nearbyStations) {
        super(message);
        this.nearbyStations = nearbyStations;
    }
}

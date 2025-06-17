package com.example.rental.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NearbyStationResponse {
    private String message;
    private List<StationDto> nearbyStations;
}

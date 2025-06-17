package com.example.stationservice.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NearbyStationResponse {
    private String message;
    private List<StationServiceDto> nearbyStations;
}

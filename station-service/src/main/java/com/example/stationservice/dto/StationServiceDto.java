package com.example.stationservice.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StationServiceDto {
    private String id;
    private String location;
    private double latitude;
    private double longitude;
    private int placeTotale;
    private List<String> vehicleIds;
}
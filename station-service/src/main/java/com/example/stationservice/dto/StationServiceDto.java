package com.example.stationservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StationServiceDto {
    private String id;
    private String location;
    private int placeTotale;
    private int placeOccupee;
}
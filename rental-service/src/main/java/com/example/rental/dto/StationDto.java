package com.example.rental.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationDto {
    private String id;
    private String location;
    private int placeTotale;
    private int placeOccupee;
}

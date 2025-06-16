package com.example.rental.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalDto {
// DTO for Rental information
    private String userId;
    private String vehiculeId;
    private String stationId;
    private String startTime;
    private String endTime;

}
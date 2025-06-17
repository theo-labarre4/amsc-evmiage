package Dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalStatisticsDTO {
    private long operationalVehicles;
    private double averageVehicleMileage;
    private long numberOfStations;
    private double averageStationOccupancyRate;
    private double totalRentalHours;
}
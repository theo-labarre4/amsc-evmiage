package Dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityStatisticsDTO {
    private String entityId;
    private String entityType; // VEHICLE, USER, STATION
    private double mileage;
    private double mileageSinceLastMaintenance;
    private double kilometersBySubscriber;
    private double occupancyRate;
}
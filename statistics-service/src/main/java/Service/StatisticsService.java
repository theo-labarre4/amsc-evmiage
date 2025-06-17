package Service;

import Dto.*;
import Repository.StatisticsRepository;
import Feign.StationClient;
import Feign.RentalClient;
import Feign.VehicleClient;
import Feign.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final VehicleClient vehicleClient;
    private final StationClient stationClient;
    private final RentalClient rentalClient;
    private final UserClient userClient;

    public GlobalStatisticsDTO getGlobalStatistics(String periodType, LocalDateTime from, LocalDateTime to) {
        // Fetch data from other services
        List<VehicleDTO> vehicles = vehicleClient.getAllVehicles();
        List<StationDTO> stations = stationClient.getAllStations();
        List<RentalDTO> rentals = rentalClient.getAllRentals();

        // Example calculations (replace Object with your DTOs and adapt fields)
        long operationalVehicles = vehicles.size();
        double averageVehicleMileage = vehicles.stream()
                .mapToDouble(VehicleDTO::getKilometrage).average().orElse(0.0);
        long numberOfStations = stations.size();
        double averageStationOccupancyRate = stations.stream()
                .mapToDouble(s -> (double) s.getPlaceOccupee() /s.getPlaceTotale()).average().orElse(0.0);
        double totalRentalHours = rentals.stream()
                /* TODO calculer rental hours mais faut parser les dates */
                .mapToDouble(r -> 2).sum();

        return GlobalStatisticsDTO.builder()
                .operationalVehicles(operationalVehicles)
                .averageVehicleMileage(averageVehicleMileage)
                .numberOfStations(numberOfStations)
                .averageStationOccupancyRate(averageStationOccupancyRate)
                .totalRentalHours(totalRentalHours)
                .build();
    }

    public EntityStatisticsDTO getEntityStatistics(String entityId, String entityType, String periodType, LocalDateTime from, LocalDateTime to) {
        switch (entityType.toUpperCase()) {
            case "VEHICLE":
                VehicleDTO vehicle = vehicleClient.getVehicleById(entityId);
                // Example: mileage since last maintenance is not available, set to 0 or fetch if possible
                return EntityStatisticsDTO.builder()
                        .entityId(entityId)
                        .entityType(entityType)
                        .mileage(vehicle.getKilometrage())
                        .mileageSinceLastMaintenance(50) // TODO: replace with real value if available
                        .build();
            case "USER":
                // Example: sum kilometers traveled by this user (subscriber) from rentals
                List<RentalDTO> rentals = rentalClient.getAllRentals();
                double kilometersBySubscriber = rentals.stream()
                        .filter(r -> r.getUserId().equals(entityId))
                        .mapToDouble(RentalDTO::getDistance) // Make sure getDistance() exists
                        .sum();
                return EntityStatisticsDTO.builder()
                        .entityId(entityId)
                        .entityType(entityType)
                        .kilometersBySubscriber(kilometersBySubscriber)
                        .build();
            case "STATION":
                StationDTO station = stationClient.getStationById(entityId);
                double occupancyRate = (double) station.getPlaceOccupee() / station.getPlaceTotale();
                return EntityStatisticsDTO.builder()
                        .entityId(entityId)
                        .entityType(entityType)
                        .occupancyRate(occupancyRate)
                        .build();
            default:
                return EntityStatisticsDTO.builder()
                        .entityId(entityId)
                        .entityType(entityType)
                        .build();
        }
    }
    
    // Method to save statistics (called by a scheduled task or event listener)
    // TODO: Implement data collection and historization
}

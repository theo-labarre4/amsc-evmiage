package Feign;

import Dto.VehicleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "vehicule-service")
public interface VehicleClient {
    @GetMapping("/vehicules")
    List<VehicleDTO> getAllVehicles();

    @GetMapping("/vehicules/{id}")
    VehicleDTO getVehicleById(String id);
}

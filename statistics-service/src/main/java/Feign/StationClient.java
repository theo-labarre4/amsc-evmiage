package Feign;

import Dto.StationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "station-service")
public interface StationClient {
    @GetMapping("/stations")
    List<StationDTO> getAllStations();

    @GetMapping("/stations/{id}")
    StationDTO getStationById(String id);
}

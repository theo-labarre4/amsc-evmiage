package Feign;

import Dto.RentalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "rental-service")
public interface RentalClient {
    @GetMapping("/rentals")
    List<RentalDTO> getAllRentals();
}

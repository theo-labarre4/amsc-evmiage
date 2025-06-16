package com.example.rental.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.rental.dto.VehiculeDto;

@FeignClient(name = "vehicule-service")
public interface VehiculeClient {

    @GetMapping("/vehicules/{id}")
    VehiculeDto getVehicule(@PathVariable("id") String id);

    @PutMapping("/vehicules/{id}")
    VehiculeDto mettreAJourVehicule(@PathVariable("id") String id, @RequestBody VehiculeDto vehiculeDto);
}

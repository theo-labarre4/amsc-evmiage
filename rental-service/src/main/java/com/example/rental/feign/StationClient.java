package com.example.rental.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.rental.dto.StationDto;


@FeignClient(name = "station-service")
public interface StationClient  {

    @GetMapping("/stations/{id}")
    StationDto getStation(@PathVariable("id") String id);

    @PutMapping("/stations/{id}")
    StationDto mettreAJourStation(@PathVariable("id") String id, @RequestBody StationDto stationDto);
}

package com.example.stationservice.repository;

import com.example.stationservice.entity.StationService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends MongoRepository<StationService, String> {

}

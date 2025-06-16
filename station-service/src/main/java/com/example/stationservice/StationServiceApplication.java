package com.example.stationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
})
public class StationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StationServiceApplication.class, args);
    }
}

package fr.miage.evmiage.decouverte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DecouverteApplication {

    public static void main(String[] args) {
        SpringApplication.run(DecouverteApplication.class, args);
    }

}

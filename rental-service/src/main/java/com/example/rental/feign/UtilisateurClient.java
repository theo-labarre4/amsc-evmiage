package com.example.rental.feign;

import com.example.rental.dto.UtilisateurDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "utilisateur-service")
public interface UtilisateurClient {

    @GetMapping("/utilisateurs/{id}")
    UtilisateurDto trouverParId(@PathVariable String id);

    @PutMapping("/utilisateurs/{id}")
    UtilisateurDto mettreAJourUtilisateur(@PathVariable String id, @RequestBody UtilisateurDto utilisateurDto);
}

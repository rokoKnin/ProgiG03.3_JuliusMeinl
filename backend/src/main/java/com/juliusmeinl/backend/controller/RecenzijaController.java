package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.dto.RecenzijaRequestDTO;
import com.juliusmeinl.backend.service.RecenzijaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reviews")
public class RecenzijaController {
    private final RecenzijaService recenzijaService;

    public RecenzijaController(RecenzijaService recenzijaService) {
        this.recenzijaService = recenzijaService;
    }

    @PostMapping("/{email}")
    public ResponseEntity<Void> dodajRecenziju(
            @PathVariable String email,
            @RequestBody RecenzijaRequestDTO recenzijaDTO) {

        recenzijaService.spremiRecenziju(email, recenzijaDTO);
        return ResponseEntity.ok().build();
    }
}

package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.dto.RecenzijaRequestDTO;
import com.juliusmeinl.backend.model.Recenzija;
import com.juliusmeinl.backend.service.RecenzijaService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Recenzija> dodajRecenziju(
            @PathVariable String email,
            @RequestBody RecenzijaRequestDTO recenzijaDTO) {


        return new ResponseEntity<>(recenzijaService.spremiRecenziju(email, recenzijaDTO), HttpStatus.CREATED);
    }
}

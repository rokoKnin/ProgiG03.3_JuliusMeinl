package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.dto.RecenzijaRequestDTO;
import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.Recenzija;
import com.juliusmeinl.backend.repository.KorisnikRepository;
import com.juliusmeinl.backend.service.RecenzijaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reviews")
@RequiredArgsConstructor
public class RecenzijaController {
    private final RecenzijaService recenzijaService;
    private final KorisnikRepository korisnikRepository;

    @PostMapping("/{email}")
    public ResponseEntity<Recenzija> dodajRecenziju(
            @PathVariable String email,
            @RequestBody RecenzijaRequestDTO recenzijaDTO) {

        return new ResponseEntity<>(recenzijaService.spremiRecenziju(email, recenzijaDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Recenzija> getRecenzija(@PathVariable String email) {
        Korisnik korisnik = korisnikRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Ne postoji korisnik s tim emailom!"));

        return new ResponseEntity<>(korisnik.getRecenzije().getFirst(), HttpStatus.OK);
    }

    @GetMapping("/random-reviews")
    public ResponseEntity<List<Recenzija>> getRandomReviews() {
        return new ResponseEntity<>(recenzijaService.generateRandom(), HttpStatus.OK);
    }

    @GetMapping("/average")
    public ResponseEntity<Double> getAverage() {
        return new ResponseEntity<>(recenzijaService.dohvatiAverage(), HttpStatus.OK);
    }
}

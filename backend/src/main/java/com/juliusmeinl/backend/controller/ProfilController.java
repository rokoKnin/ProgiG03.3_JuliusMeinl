package com.juliusmeinl.backend.controller;


import com.juliusmeinl.backend.dto.KorisnikRequestDTO;
import com.juliusmeinl.backend.dto.KorisnikResponseDTO;
import com.juliusmeinl.backend.dto.ProfilRezervacijeResponseDTO;
import com.juliusmeinl.backend.model.Drzava;
import com.juliusmeinl.backend.repository.DrzavaRepository;
import com.juliusmeinl.backend.service.KorisnikService;
import com.juliusmeinl.backend.service.RezervacijaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfilController {
    private final KorisnikService korisnikService;
    private final RezervacijaService rezervacijaService;
    private final DrzavaRepository drzavaRepository;

    public ProfilController(KorisnikService korisnikService, RezervacijaService rezervacijaService, DrzavaRepository drzavaRepository) {
        this.korisnikService = korisnikService;
        this.rezervacijaService = rezervacijaService;
        this.drzavaRepository = drzavaRepository;
    }


    @PostMapping("/{email}")
    public KorisnikResponseDTO prikaziKorisnika(@PathVariable String email) {
       return korisnikService.ispisiKorisnika(email);
    }

    @PutMapping("/edit")
    public KorisnikResponseDTO izmjeniKorisnika(@RequestBody KorisnikRequestDTO korisnikRequestDTO) {
        return korisnikService.izmjeniKorisnika(korisnikRequestDTO);
    }

    @GetMapping("/countries-list")
    public ResponseEntity<List<String>> drzaveLista() {
        return new ResponseEntity<>(drzavaRepository.findAllNazivDrzave(), HttpStatus.OK);
    }

    @GetMapping("/reservations/{korisnik_email}")
    public ResponseEntity<List<ProfilRezervacijeResponseDTO>> prosleRezervacije(@PathVariable String korisnik_email) {
        return new ResponseEntity<>(rezervacijaService.dohvatiRezervacije(korisnik_email), HttpStatus.OK);
    }

}

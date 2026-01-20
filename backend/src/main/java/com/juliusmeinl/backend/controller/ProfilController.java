package com.juliusmeinl.backend.controller;


import com.juliusmeinl.backend.dto.KorisnikRequestDTO;
import com.juliusmeinl.backend.dto.KorisnikResponseDTO;
import com.juliusmeinl.backend.dto.ProfilRezervacijeResponseDTO;
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

    public ProfilController(KorisnikService korisnikService, RezervacijaService rezervacijaService) {
        this.korisnikService = korisnikService;
        this.rezervacijaService = rezervacijaService;
    }

    @PostMapping("/{email}")
    public KorisnikResponseDTO prikaziKorisnika(@PathVariable String email) {
       return korisnikService.ispisiKorisnika(email);
    }

    @PutMapping("/edit")
    public KorisnikResponseDTO izmjeniKorisnika(@RequestBody KorisnikRequestDTO korisnikRequestDTO) {
        return korisnikService.izmjeniKorisnika(korisnikRequestDTO);
    }

    @PostMapping("/reservationsPassed/{korisnik_email}")
    public ResponseEntity<List<ProfilRezervacijeResponseDTO>> prosleRezervacije(@PathVariable String korisnikEmail) {
        return new ResponseEntity<>(rezervacijaService.dohvatiProsleRezervacijen(korisnikEmail), HttpStatus.OK);
    }

}

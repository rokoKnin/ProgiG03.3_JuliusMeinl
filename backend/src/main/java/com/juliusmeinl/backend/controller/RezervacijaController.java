package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.dto.RezervacijaRequestDTO;
import com.juliusmeinl.backend.model.Rezervacija;
import com.juliusmeinl.backend.service.KorisnikService;
import com.juliusmeinl.backend.service.RezervacijaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reservations")
public class RezervacijaController {
    private final RezervacijaService rezervirajService;
    private final KorisnikService korisnikService;

    public RezervacijaController(RezervacijaService rezervirajService, KorisnikService korisnikService) {
        this.rezervirajService = rezervirajService;
        this.korisnikService = korisnikService;
    }

    @PostMapping
    public void napraviRezervaciju(@RequestBody RezervacijaRequestDTO rezervacijaRequestDTO) {
        //napravi generalnu rezervaciju
        rezervirajService.kreirajRezervaciju(korisnikService.trenutniKorisnikId());

    }


    //prvo napravi rezervaciju u bazi, treba mi korisnik_id moram skuzit kako ga dobit
    //optimizirat pronalazak soba i dodat ij u rezervirajSobu
    //stavit dodatan sadrzaj u bazu, promijenit da dodatni sadrzaj ima cijenu a rezervacija nema i da nije datum od do
    //nekako zbrojit cijenu azurirat rezervaciju


}

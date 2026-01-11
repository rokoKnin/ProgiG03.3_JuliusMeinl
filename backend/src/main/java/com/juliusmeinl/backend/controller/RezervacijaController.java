package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.dto.RezervacijaRequestDTO;
import com.juliusmeinl.backend.model.Rezervacija;
import com.juliusmeinl.backend.service.KorisnikService;
import com.juliusmeinl.backend.service.RezervacijaService;
import com.juliusmeinl.backend.service.SobaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/reservations")
public class RezervacijaController {
    private final RezervacijaService rezervirajService;
    private final KorisnikService korisnikService;
    private final SobaService sobaService;

    public RezervacijaController(RezervacijaService rezervirajService, KorisnikService korisnikService, SobaService sobaService) {
        this.rezervirajService = rezervirajService;
        this.korisnikService = korisnikService;
        this.sobaService = sobaService;
    }

    @PostMapping
    public void napraviRezervaciju(@RequestBody RezervacijaRequestDTO rezervacijaRequestDTO) {
        //napravi generalnu rezervaciju
        Integer rezervacijaId =  rezervirajService.kreirajRezervaciju(korisnikService.trenutniKorisnikId());

        //ako nije rezervacija za sobu skipam ovo
        if(!rezervacijaRequestDTO.getSobe().isEmpty()) {
            List<Integer> dodijeljenjeSobeId = sobaService.dohvatiSobe(rezervacijaRequestDTO);
            rezervirajService.rezervirajSobe(rezervacijaId, dodijeljenjeSobeId, rezervacijaRequestDTO.getDatumSobeOd(), rezervacijaRequestDTO.getDatumSobeDo());
        }
        rezervirajService.rezervirajSadrzaj(rezervacijaId, rezervacijaRequestDTO);
    }


}

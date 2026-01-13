package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.dto.RezervacijaRequestDTO;
import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.service.KorisnikService;
import com.juliusmeinl.backend.service.RezervacijaService;
import com.juliusmeinl.backend.service.SobaService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{korisnikEmail}")
    public void napraviRezervaciju(@RequestBody RezervacijaRequestDTO rezervacijaRequestDTO, @PathVariable String korisnikEmail) {
        Korisnik korisnik = korisnikService.findByEmail(korisnikEmail).orElseThrow(() -> new RuntimeException("Wrong email provided!\n"));

        Integer rezervacijaId =  rezervirajService.kreirajRezervaciju(korisnik.getId());

        //ako nije rezervacija za sobu skipam ovo
        if(!rezervacijaRequestDTO.getSobe().isEmpty()) {
            List<Integer> dodijeljenjeSobeId = sobaService.dohvatiSobe(rezervacijaRequestDTO);
            rezervirajService.rezervirajSobe(rezervacijaId, dodijeljenjeSobeId, rezervacijaRequestDTO.getDatumOd(), rezervacijaRequestDTO.getDatumDo());
        }
        rezervirajService.rezervirajSadrzaj(rezervacijaId, rezervacijaRequestDTO);
    }


}

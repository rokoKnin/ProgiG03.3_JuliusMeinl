package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.dto.RezervacijaRequestDTO;
import com.juliusmeinl.backend.model.Rezervacija;
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

    @PostMapping
    public void napraviRezervaciju(@RequestBody RezervacijaRequestDTO rezervacijaRequestDTO) {
        //napravi generalnu rezervaciju
        Integer rezervacijaId =  rezervirajService.kreirajRezervaciju(korisnikService.trenutniKorisnikId());

        //ako nije rezervacija za sobu skipam ovo
        if(!rezervacijaRequestDTO.getSobe().isEmpty()) {
            List<Integer> dodijeljenjeSobeId = sobaService.dohvatiSobe(rezervacijaRequestDTO);
            rezervirajService.rezervirajSobe(rezervacijaId, dodijeljenjeSobeId, rezervacijaRequestDTO.getDatumOd(), rezervacijaRequestDTO.getDatumDo());
        }
        rezervirajService.rezervirajSadrzaj(rezervacijaId, rezervacijaRequestDTO);
    }
    @GetMapping("/all")
    public List<Rezervacija> sveRezervacije() {
        List<Rezervacija> lista = rezervirajService.dohvatiSveRezervacije();
        // inicijaliziraj lazy kolekcije
        lista.forEach(r -> {
            r.getSobe().size();     // pokreće dohvat soba
            r.getSadrzaji().size(); // pokreće dohvat dodatnog sadržaja
        });
        return lista;
    }


    @PutMapping("/{id}")
    public Rezervacija azurirajRezervaciju(@PathVariable Integer id, @RequestBody Rezervacija rezervacijaInput) {
        return rezervirajService.azurirajRezervaciju(id, rezervacijaInput);
    }
}

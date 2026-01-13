package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.dto.DodatniSadrzajResponseDTO;
import com.juliusmeinl.backend.dto.RezervacijaRequestDTO;
import com.juliusmeinl.backend.service.DodatniSadrzajService;
import com.juliusmeinl.backend.service.KorisnikService;
import com.juliusmeinl.backend.service.RezervacijaService;
import com.juliusmeinl.backend.service.SobaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/reservations")
public class RezervacijaController {
    private final RezervacijaService rezervirajService;
    private final KorisnikService korisnikService;
    private final SobaService sobaService;
    private final DodatniSadrzajService sadrzajService;

    public RezervacijaController(RezervacijaService rezervirajService, KorisnikService korisnikService, SobaService sobaService, DodatniSadrzajService sadrzajService) {
        this.rezervirajService = rezervirajService;
        this.korisnikService = korisnikService;
        this.sobaService = sobaService;
        this.sadrzajService = sadrzajService;
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

    @GetMapping("/additional-services")
    public List<DodatniSadrzajResponseDTO> rezervacijaSadrzajInformacije() {
        return sadrzajService.informacijeSadrzaj();
    }


}

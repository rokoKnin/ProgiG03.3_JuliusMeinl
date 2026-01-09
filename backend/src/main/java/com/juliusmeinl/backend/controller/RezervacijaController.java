package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.service.RezervacijaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reservations")
public class RezervacijaController {
    private final RezervacijaService rezervirajSobuService;

    public RezervacijaController(RezervacijaService rezervirajSobuService) {
        this.rezervirajSobuService = rezervirajSobuService;
    }


    //prvo napravi rezervaciju u bazi, treba mi korisnik_id moram skuzit kako ga dobit
    //optimizirat pronalazak soba i dodat ij u rezervirajSobu
    //stavit dodatan sadrzaj u bazu, promijenit da dodatni sadrzaj ima cijenu a rezervacija nema i da nije datum od do
    //nekako zbrojit cijenu azurirat rezervaciju


}

package com.juliusmeinl.backend.controller;
import com.juliusmeinl.backend.service.SobaService;
import com.juliusmeinl.backend.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class SobaController {

    private final SobaService sobaService;

    public SobaController(SobaService sobaService) {
        this.sobaService = sobaService;
    }

    @GetMapping
    public ResponseEntity<List<Soba>> getSveSobe() {
        return ResponseEntity.ok(sobaService.getSveSobe());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Soba> getSobaById(@PathVariable Integer id) {
        return ResponseEntity.ok(sobaService.getSobaById(id));
    }

    @PostMapping
    public Soba dodajSobu(@RequestBody Map<String, String> sobaMap) {
        Soba soba = new Soba();
        soba.setBrojSobe(sobaMap.get("broj_sobe"));
        soba.setKat(Integer.valueOf(sobaMap.get("kat")));
        soba.setBalkon(Boolean.valueOf(sobaMap.get("balkon")));
        soba.setVrsta(VrstaSobe.valueOf(sobaMap.get("vrsta")));
        soba.setPogledNaMore(Boolean.valueOf(sobaMap.get("pogledMore")));
        soba.setStatus(StatusSobe.DOSTUPNA);

        BigDecimal cijena = BigDecimal.ZERO;

        VrstaSobe vrsta = soba.getVrsta();
        int kat = soba.getKat();
        boolean balkon = soba.getBalkon();
        boolean pogledNaMore = soba.getPogledNaMore();

// DVOKREVETNA_KING
        if (vrsta == VrstaSobe.DVOKREVETNA_KING) {
            if (kat == 1) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("120.00");
                else if (balkon && !pogledNaMore) cijena = new BigDecimal("105.00");
                else if (!balkon && pogledNaMore) cijena = new BigDecimal("110.00");
                else cijena = new BigDecimal("100.00");
            } else if (kat == 2) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("130.00");
                else if (balkon && !pogledNaMore) cijena = new BigDecimal("115.00");
                else if (!balkon && pogledNaMore) cijena = new BigDecimal("120.00");
                else cijena = new BigDecimal("110.00");
            } else if (kat == 3) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("140.00");
                else if (balkon && !pogledNaMore) cijena = new BigDecimal("125.00");
                else if (!balkon && pogledNaMore) cijena = new BigDecimal("130.00");
                else cijena = new BigDecimal("120.00");
            }
        }

// DVOKREVETNA_TWIN
        else if (vrsta == VrstaSobe.DVOKREVETNA_TWIN) {
            if (kat == 1) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("120.00");
                else if (balkon && !pogledNaMore) cijena = new BigDecimal("105.00");
                else if (!balkon && pogledNaMore) cijena = new BigDecimal("110.00");
                else cijena = new BigDecimal("100.00");
            } else if (kat == 2) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("130.00");
                else if (balkon && !pogledNaMore) cijena = new BigDecimal("115.00");
                else if (!balkon && pogledNaMore) cijena = new BigDecimal("120.00");
                else cijena = new BigDecimal("110.00");
            } else if (kat == 3) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("140.00");
                else if (balkon && !pogledNaMore) cijena = new BigDecimal("125.00");
                else if (!balkon && pogledNaMore) cijena = new BigDecimal("130.00");
                else cijena = new BigDecimal("120.00");
            }
        }

// TROKREVETNA
        else if (vrsta == VrstaSobe.TROKREVETNA) {
            if (kat == 1) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("150.00");
                else if (balkon && !pogledNaMore) cijena = new BigDecimal("135.00");
                else if (!balkon && pogledNaMore) cijena = new BigDecimal("140.00");
                else cijena = new BigDecimal("130.00");
            } else if (kat == 2) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("160.00");
                else if (balkon && !pogledNaMore) cijena = new BigDecimal("145.00");
                else if (!balkon && pogledNaMore) cijena = new BigDecimal("150.00");
                else cijena = new BigDecimal("140.00");
            } else if (kat == 3) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("170.00");
                else if (balkon && !pogledNaMore) cijena = new BigDecimal("155.00");
                else if (!balkon && pogledNaMore) cijena = new BigDecimal("160.00");
                else cijena = new BigDecimal("150.00");
            }
        }

// PENTHOUSE (kat 4, svi imaju balkon i pogled)
        else if (vrsta == VrstaSobe.PENTHOUSE) {
            if (kat == 4) {
                if (sobaMap.get("kapacitet").toString().equals("2")) cijena = new BigDecimal("250.00");
                else cijena = new BigDecimal("300.00");
            }
        }

// Postavi cijenu u sobu
        soba.setCijena(cijena);

        int kapacitet;
        switch (vrsta) {
            case DVOKREVETNA_KING:
            case DVOKREVETNA_TWIN:
                kapacitet = 2;
                break;
            case TROKREVETNA:
            case PENTHOUSE:
                kapacitet = 3;
                break;
            default:
                kapacitet = 1; // fallback, ako je neka neočekivana vrsta
        }

        soba.setKapacitet(kapacitet);
        System.out.println(soba.getBrojSobe() + soba.getKat() + soba.getCijena() + soba.getVrsta() + soba.getBalkon());
        return  sobaService.spremiSobu(soba);
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> obrisiSobu(@PathVariable Integer id) {
            sobaService.obrisiSobu(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Soba> azurirajSobu(
            @PathVariable Integer id,
            @RequestBody Map<String, String> sobaMap) {

        Optional<Soba> optionalSoba = sobaService.pronadiPoId(id);
        if (optionalSoba.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Soba soba = optionalSoba.get();

        // Ažuriraj polja
        soba.setBrojSobe(sobaMap.get("broj_sobe"));
        soba.setKat(Integer.valueOf(sobaMap.get("kat")));
        soba.setBalkon(Boolean.valueOf(sobaMap.get("balkon")));
        soba.setVrsta(VrstaSobe.valueOf(sobaMap.get("vrsta")));
        soba.setPogledNaMore(Boolean.valueOf(sobaMap.get("pogledMore")));

        // Reizračunaj cijenu (možeš ponoviti istu logiku kao u POST metodi)
        BigDecimal novaCijena = sobaService.izracunajCijenu(
                soba.getVrsta(),
                soba.getKat(),
                soba.imaBalkon(),
                soba.imaPogledNaMore()
        );
        soba.setCijena(novaCijena);

        // Spremi promjene
        Soba azurirana = sobaService.spremiSobu(soba);
        return ResponseEntity.ok(azurirana);
    }

    @PostMapping("/available")
    public List<Map<String, Object>> dostupneSobe(@RequestBody Map<String, String> dateMap) {
        // moram napravit counter za svaku vrstu sobe mogucu
        LocalDate datumOd = LocalDate.parse(dateMap.get("datumOd"));
        LocalDate datumDo = LocalDate.parse(dateMap.get("datumDo"));

        return sobaService.dostupneSobe(datumOd, datumDo);
    }


}

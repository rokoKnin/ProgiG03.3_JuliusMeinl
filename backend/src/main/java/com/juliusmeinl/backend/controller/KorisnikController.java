package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.MjestoId;
import com.juliusmeinl.backend.service.KorisnikService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class KorisnikController {

    private final KorisnikService korisnikService;

    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

    @PostMapping
    public Korisnik kreirajKorisnika(@RequestBody Map<String, String> userMap) {

        Korisnik korisnik = new Korisnik();
        korisnik.setIme(userMap.get("ime"));
        korisnik.setPrezime(userMap.get("prezime"));
        korisnik.setEmail(userMap.get("email"));
        korisnik.setTelefon(userMap.get("telefon"));
        korisnik.setOvlast("GOST");

        String postBr = userMap.get("postBr");
        String nazMjesto = userMap.get("nazMjesto");

        MjestoId mjestoId = new MjestoId(postBr, nazMjesto);
        mjestoId.setNazMjesto(mjestoId.getNazMjesto().toLowerCase().replaceAll(" ", "")); //prilagodba input imena mjesta za bazu

        String nazDrzava = userMap.get("nazDrzava");

        return korisnikService.spremiKorisnika(korisnik, mjestoId, nazDrzava);
    }
    @GetMapping("/check-vlasnik")
    public boolean provjeriVlasnika(@RequestParam String email) {
        return korisnikService.korisnikJeVlasnik(email);
    }
}




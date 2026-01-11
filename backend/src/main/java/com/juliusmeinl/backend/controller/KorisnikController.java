package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.MjestoId;
import com.juliusmeinl.backend.model.UlogaKorisnika;
import com.juliusmeinl.backend.service.KorisnikService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class KorisnikController {

    private final KorisnikService korisnikService;

    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

    @GetMapping("/info")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }

    @PostMapping
    public ResponseEntity<Korisnik> kreirajKorisnika(@RequestBody Map<String, String> userMap) {
        Korisnik korisnik = new Korisnik();
        korisnik.setIme(userMap.get("ime"));
        korisnik.setPrezime(userMap.get("prezime"));
        korisnik.setEmail(userMap.get("email"));
        korisnik.setTelefon(userMap.get("telefon"));
        korisnik.setOvlast(UlogaKorisnika.GOST);

        String postBr = userMap.get("postBr");
        String nazMjesto = userMap.get("nazMjesto");

        MjestoId mjestoId = new MjestoId(postBr, nazMjesto);
        mjestoId.setNazMjesto(mjestoId.getNazMjesto().toLowerCase().replaceAll(" ", "")); //prilagodba input imena mjesta za bazu

        String nazDrzava = userMap.get("nazDrzava");

        return new ResponseEntity<>(korisnikService.spremiKorisnika(korisnik, mjestoId, nazDrzava), HttpStatus.CREATED);
    }
}




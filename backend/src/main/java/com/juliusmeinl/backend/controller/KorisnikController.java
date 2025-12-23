package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.MjestoId;
import com.juliusmeinl.backend.service.AuthService;
import com.juliusmeinl.backend.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class KorisnikController {

    private AuthService authService;

    private final KorisnikService korisnikService;

    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

//    @GetMapping("/info")
//    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
//        return principal.getAttributes();
//    }

//    @GetMapping("/info")
//    public Map<String, Object> getProfile() {
//        Korisnik loggedInUser = authService.getLoggedInUser()
//                .orElseThrow(RuntimeException::new);
//        Map<String, Object> profile = korisnikService.getProfileMap(loggedInUser);
//        return profile;
//    }


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




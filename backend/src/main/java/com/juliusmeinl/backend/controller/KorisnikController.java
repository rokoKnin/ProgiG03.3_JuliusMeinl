package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.MjestoId;
import com.juliusmeinl.backend.service.AuthService;
import com.juliusmeinl.backend.service.KorisnikService;
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
//
//        String email = ((OAuth2User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email");
//        if (email == null) {
//            return new ResponseEntity<>("",HttpStatus.OK);
//        }
//        return new ResponseEntity<>("{\"email\": \"" + email + "\"}", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Korisnik> kreirajKorisnika(@RequestBody Korisnik korisnik) {
        return new ResponseEntity<>(korisnikService.spremiKorisnika(korisnik), HttpStatus.CREATED);
    }
}




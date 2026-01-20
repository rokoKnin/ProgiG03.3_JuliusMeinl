package com.juliusmeinl.backend.controller;


import com.juliusmeinl.backend.dto.KorisnikResponseDTO;
import com.juliusmeinl.backend.service.KorisnikService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfilController {
    private final KorisnikService korisnikService;

    public ProfilController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

    @PostMapping("/{email}")
    public KorisnikResponseDTO prikaziKorisnika(@PathVariable String email) {
       return korisnikService.ispisiKorisnika(email);
    }

    @PostMapping("/edit/{email}")  // ovo nije dobro
    public void izmjeniKorisnika(@PathVariable String email) {

    }

}

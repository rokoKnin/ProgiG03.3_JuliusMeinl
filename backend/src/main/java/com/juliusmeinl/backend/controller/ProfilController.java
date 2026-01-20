package com.juliusmeinl.backend.controller;


import com.juliusmeinl.backend.dto.KorisnikRequestDTO;
import com.juliusmeinl.backend.dto.KorisnikResponseDTO;
import com.juliusmeinl.backend.service.KorisnikService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/edit")
    public KorisnikResponseDTO izmjeniKorisnika(@RequestBody KorisnikRequestDTO korisnikRequestDTO) {
        return korisnikService.izmjeniKorisnika(korisnikRequestDTO);
    }


}

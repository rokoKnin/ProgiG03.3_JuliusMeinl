package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/korisnici")
public class KorisnikController {

    private final KorisnikService korisnikService;

    @Autowired
    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

    // GET all korisnici
    @GetMapping
    public List<Korisnik> getAllKorisnici() {
        return korisnikService.getAllKorisnici();
    }

    // GET korisnik by ID
    @GetMapping("/{id}")
    public ResponseEntity<Korisnik> getKorisnikById(@PathVariable Integer id) {
        return korisnikService.getKorisnikById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create new korisnik
    @PostMapping
    public Korisnik createKorisnik(@RequestBody Korisnik korisnik) {
        return korisnikService.saveKorisnik(korisnik);
    }

    // PUT update korisnik by ID
    @PutMapping("/{id}")
    public ResponseEntity<Korisnik> updateKorisnik(@PathVariable Integer id, @RequestBody Korisnik korisnik) {
        try {
            Korisnik updatedKorisnik = korisnikService.updateKorisnik(id, korisnik);
            return ResponseEntity.ok(updatedKorisnik);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE korisnik by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKorisnik(@PathVariable Integer id) {
        korisnikService.deleteKorisnik(id);
        return ResponseEntity.noContent().build();
    }

    // GET korisnik by email
    @GetMapping("/email/{email}")
    public ResponseEntity<Korisnik> getKorisnikByEmail(@PathVariable String email) {
        return korisnikService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET korisnik by telefon
    @GetMapping("/telefon/{telefon}")
    public ResponseEntity<Korisnik> getKorisnikByTelefon(@PathVariable String telefon) {
        return korisnikService.findByTelefon(telefon)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

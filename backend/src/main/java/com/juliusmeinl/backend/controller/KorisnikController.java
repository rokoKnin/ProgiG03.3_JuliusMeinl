package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.service.KorisnikService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class KorisnikController {

    private final KorisnikService korisnikService;

    @GetMapping
    public List<Map<String, Object>> getAllUsers() {
        // Ovo vraća korisnike spremne za frontend (bez parsiranja uloge)
        return korisnikService.getAllUsersForFrontend();
    }

    @PutMapping
    public ResponseEntity<Korisnik> kreirajKorisnika(@RequestBody Korisnik korisnik) {
        return new ResponseEntity<>(korisnikService.spremiKorisnika(korisnik), HttpStatus.CREATED);
    }


    @PutMapping("/{userId}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Integer userId, @RequestBody Map<String, String> body) {
        String novaUloga = body.get("uloga");
        if (novaUloga == null) return ResponseEntity.badRequest().build();

        Korisnik korisnik = korisnikService.updateRole(userId, novaUloga);
        return ResponseEntity.ok(Map.of("id", korisnik.getId(), "uloga", korisnik.getOvlast().name()));
    }


    @GetMapping("/check-vlasnik")
    public boolean provjeriVlasnika(@RequestParam String email) {
        return korisnikService.korisnikJeVlasnik(email);
    }


    @GetMapping("/export")
    public ResponseEntity<ByteArrayResource> exportUsers(@RequestParam String format) {
        ByteArrayResource file;
        MediaType mediaType;

        switch (format.toLowerCase()) {
            case "xlsx":
                file = korisnikService.exportUsersXLSX();
                mediaType = MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                break;
            case "pdf":
                file = korisnikService.exportUsersPDF();
                mediaType = MediaType.APPLICATION_PDF;
                break;
            case "xml":
                file = korisnikService.exportUsersXML();
                mediaType = MediaType.APPLICATION_XML;
                break;
            default:
                throw new RuntimeException("Nepodržani format");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users." + format)
                .contentType(mediaType)
                .body(file);
    }
}

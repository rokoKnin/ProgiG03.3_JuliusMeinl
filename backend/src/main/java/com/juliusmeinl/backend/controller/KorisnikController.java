package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.MjestoId;
import com.juliusmeinl.backend.model.UlogaKorisnika;
import com.juliusmeinl.backend.service.KorisnikService;
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


    @GetMapping
    public List<Map<String, Object>> getAllUsers() {
        return korisnikService.getAllUsersForFrontend();
    }


    @PostMapping
    public ResponseEntity<Korisnik> kreirajKorisnika(@RequestBody Map<String, String> userMap) {
        Korisnik korisnik = new Korisnik();
        korisnik.setIme(userMap.get("ime"));
        korisnik.setPrezime(userMap.get("prezime"));
        korisnik.setEmail(userMap.get("email"));
        korisnik.setTelefon(userMap.get("telefon"));
        korisnik.setOvlast(
                UlogaKorisnika.GOST);

        String postBr = userMap.get("postBr");
        String nazMjesto = userMap.get("nazMjesto");
        MjestoId mjestoId = new MjestoId(postBr, nazMjesto);
        mjestoId.setNazMjesto(mjestoId.getNazMjesto().toLowerCase().replaceAll(" ", "")); // prilagodba inputa

        String nazDrzava = userMap.get("nazDrzava");

        Korisnik savedUser = korisnikService.spremiKorisnika(korisnik, mjestoId, nazDrzava);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
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

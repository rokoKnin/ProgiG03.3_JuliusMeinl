package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.dto.RezervacijaRequestDTO;
import com.juliusmeinl.backend.dto.RezervacijaResponseDTO;
import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.Rezervacija;
import com.juliusmeinl.backend.service.KorisnikService;
import com.juliusmeinl.backend.service.RezervacijaService;
import com.juliusmeinl.backend.service.SobaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("api/reservations")
public class RezervacijaController {
    private final RezervacijaService rezervirajService;
    private final KorisnikService korisnikService;
    private final SobaService sobaService;

    public RezervacijaController(RezervacijaService rezervirajService, KorisnikService korisnikService, SobaService sobaService) {
        this.rezervirajService = rezervirajService;
        this.korisnikService = korisnikService;
        this.sobaService = sobaService;
    }

    @PostMapping
    public void napraviRezervaciju(@RequestBody RezervacijaRequestDTO rezervacijaRequestDTO) {
        //napravi generalnu rezervaciju
        Integer rezervacijaId =  rezervirajService.kreirajRezervaciju(korisnikService.trenutniKorisnikId());

        //ako nije rezervacija za sobu skipam ovo
        if(!rezervacijaRequestDTO.getSobe().isEmpty()) {
            List<Integer> dodijeljenjeSobeId = sobaService.dohvatiSobe(rezervacijaRequestDTO);
            rezervirajService.rezervirajSobe(rezervacijaId, dodijeljenjeSobeId, rezervacijaRequestDTO.getDatumOd(), rezervacijaRequestDTO.getDatumDo());
        }
        rezervirajService.rezervirajSadrzaj(rezervacijaId, rezervacijaRequestDTO);
    }
    @GetMapping("/all")
    public List<RezervacijaResponseDTO> sveRezervacije() {
        return rezervirajService.dohvatiSveRezervacijeDTO();
    }




    @PutMapping("/{id}")
    public Rezervacija azurirajRezervaciju(@PathVariable Integer id, @RequestBody Rezervacija rezervacijaInput) {
        return rezervirajService.azurirajRezervaciju(id, rezervacijaInput);
    }


    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportReservations(@RequestParam String format) {
        try {
            byte[] data;
            String filename;
            MediaType mediaType;

            switch (format.toLowerCase()) {
                case "pdf":
                    data = rezervirajService.exportPdf();
                    filename = "reservations.pdf";
                    mediaType = MediaType.APPLICATION_PDF;
                    break;
                case "xlsx":
                    data = rezervirajService.exportXlsx();
                    filename = "reservations.xlsx";
                    mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    break;
                case "xml":
                    data = rezervirajService.exportXml();
                    filename = "reservations.xml";
                    mediaType = MediaType.APPLICATION_XML;
                    break;
                default:
                    throw new IllegalArgumentException("Nepoznat format: " + format);
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(mediaType)
                    .body(new InputStreamResource(new java.io.ByteArrayInputStream(data)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

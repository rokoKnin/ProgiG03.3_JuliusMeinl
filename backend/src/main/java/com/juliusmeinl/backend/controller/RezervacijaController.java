package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.dto.AvailableRoomDTO;
import com.juliusmeinl.backend.dto.DodatniSadrzajResponseDTO;
import com.juliusmeinl.backend.dto.RezervacijaRequestDTO;
import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.dto.RezervacijaResponseDTO;
import com.juliusmeinl.backend.dto.RoomAvailabilityRequest;
import com.juliusmeinl.backend.dto.UpdateRoomRequest;
import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.service.*;
import com.juliusmeinl.backend.model.Rezervacija;
import org.springframework.security.access.prepost.PreAuthorize;
import com.juliusmeinl.backend.dto.RoomAvailabilityRequest;
import com.juliusmeinl.backend.dto.UpdateRoomRequest;
import com.juliusmeinl.backend.dto.AvailableRoomDTO;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/reservations")
public class RezervacijaController {

    private final RezervacijaService rezervirajService;
    private final KorisnikService korisnikService;
    private final SobaService sobaService;
    private final DodatniSadrzajService sadrzajService;
    private final EmailService emailService;



    @PostMapping("/{korisnikEmail}")
    public void napraviRezervaciju(@RequestBody RezervacijaRequestDTO rezervacijaRequestDTO, @PathVariable String korisnikEmail) {
        Korisnik korisnik = korisnikService.findByEmail(korisnikEmail).orElseThrow(() -> new RuntimeException("email ne postoji"));

        Integer rezervacijaId =  rezervirajService.kreirajRezervaciju(korisnik.getId());

        //ako nije rezervacija za sobu skipam ovo
        if(!rezervacijaRequestDTO.getSobe().isEmpty()) {
            List<Integer> dodijeljenjeSobeId = sobaService.dohvatiSobe(rezervacijaRequestDTO);
            rezervirajService.rezervirajSobe(rezervacijaId, dodijeljenjeSobeId, rezervacijaRequestDTO.getDatumOd(), rezervacijaRequestDTO.getDatumDo());
        }
        rezervirajService.rezervirajSadrzaj(rezervacijaId, rezervacijaRequestDTO);

        emailService.posaljiRezervacijuMail(rezervacijaId, rezervacijaRequestDTO);
    }

    @GetMapping("/additional-services")
    public List<DodatniSadrzajResponseDTO> rezervacijaSadrzajInformacije() {
        return sadrzajService.informacijeSadrzaj();
    }

    @PreAuthorize("hasAuthority('receptionist:read')")
    @GetMapping("/all")
    public List<RezervacijaResponseDTO> sveRezervacije() {
        return rezervirajService.dohvatiSveRezervacijeDTO();
    }

    @PreAuthorize("hasAuthority('receptionist:update')")
    @PutMapping("/{id}")
    public Rezervacija azurirajRezervaciju(@PathVariable Integer id, @RequestBody Rezervacija rezervacijaInput) {
        return rezervirajService.azurirajRezervaciju(id, rezervacijaInput);
    }

    @PreAuthorize("hasAuthority('receptionist:read')")
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

    @PreAuthorize("hasAuthority('receptionist:read')")
    @PostMapping("/available-rooms")
    public ResponseEntity<List<AvailableRoomDTO>> getAvailableRooms(@RequestBody RoomAvailabilityRequest request) {
        try {
            // Get available rooms from service
            List<AvailableRoomDTO> availableRooms = rezervirajService.getAvailableRooms(
                request.getDateFrom(),
                request.getDateTo(),
                request.getCurrentRoomId()
            );
            return ResponseEntity.ok(availableRooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAuthority('receptionist:update')")
    @PutMapping("/{reservationId}/room")
    public ResponseEntity<?> updateReservationRoom(
        @PathVariable Integer reservationId,
        @RequestBody UpdateRoomRequest request
    ) {
        try {
            boolean success = rezervirajService.updateReservationRoom(
                reservationId,
                request.getRoomIndex(),
                request.getNewRoomId(),
                request.getNewRoomNumber(),
                request.getNewRoomType()
            );

            if (success) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body("Greška pri ažuriranju sobe");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Greška: " + e.getMessage());
        }
    }
}

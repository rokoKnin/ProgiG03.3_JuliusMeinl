package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.service.RezervacijaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/recepcionist")
@RequiredArgsConstructor
public class RecepcionistController {

    private final RezervacijaService rezervacijaService;

    @GetMapping("/export")
    public ResponseEntity<ByteArrayResource> exportTodayReservationsPdf(@RequestParam String format) {
        if (!format.equalsIgnoreCase("pdf")) {
            return ResponseEntity.badRequest().build(); // only PDF supported for now
        }

        ByteArrayResource resource = rezervacijaService.exportDanasnjeRezervacijePdf();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=plan_rada.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(resource.contentLength())
                .body(resource);
    }
}

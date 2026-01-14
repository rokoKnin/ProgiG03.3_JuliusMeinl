package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.model.DodatniSadrzaj;
import com.juliusmeinl.backend.model.StatusDodatniSadrzaj;
import com.juliusmeinl.backend.service.DodatniSadrzajService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/extraContentEdit")
@CrossOrigin(origins = "*")
public class DodatniSadrzajController{

    private final DodatniSadrzajService dodatniSadrzajService;

    public DodatniSadrzajController(DodatniSadrzajService dodatniSadrzajService) {
        this.dodatniSadrzajService = dodatniSadrzajService;
    }

    @GetMapping
    public ResponseEntity<List<DodatniSadrzaj>> getAll() {
        return ResponseEntity.ok(dodatniSadrzajService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DodatniSadrzaj> update(@PathVariable Integer id, @RequestBody Map<String, Object> payload) {
        // Frontend šalje cijeli objekt, izvlačimo samo cijenu i status
        BigDecimal price = new BigDecimal(payload.get("cijena").toString());
        String statusStr = payload.get("status").toString();

        return ResponseEntity.ok(dodatniSadrzajService.update(id, price, statusStr));
    }

    @GetMapping("/export")
    public ResponseEntity<ByteArrayResource> export(@RequestParam String format) {
        ByteArrayResource file;
        MediaType mediaType;

        switch (format.toLowerCase()) {
            case "xlsx" -> {
                file = dodatniSadrzajService.exportXLSX();
                mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            }
            case "pdf" -> {
                file = dodatniSadrzajService.exportPDF();
                mediaType = MediaType.APPLICATION_PDF;
            }
            case "xml" -> {
                file = dodatniSadrzajService.exportXML();
                mediaType = MediaType.APPLICATION_XML;
            }
            default -> throw new RuntimeException("Nepodržani format");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dodatni_sadrzaj." + format)
                .contentType(mediaType)
                .body(file);
    }
}
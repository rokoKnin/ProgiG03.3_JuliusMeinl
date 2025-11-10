package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.model.Soba;
import com.juliusmeinl.backend.model.VrstaSobe;
import com.juliusmeinl.backend.service.SobaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sobe")
@CrossOrigin(origins = "*")
public class SobaController {

    private final SobaService sobaService;

    public SobaController(SobaService sobaService) {
        this.sobaService = sobaService;
    }

    @GetMapping
    public ResponseEntity<List<Soba>> getSveSobe() {
        return ResponseEntity.ok(sobaService.getSveSobe());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Soba> getSobaById(@PathVariable Integer id) {
        return ResponseEntity.ok(sobaService.getSobaById(id));
    }

    @GetMapping("/vrsta/{vrsta}")
    public ResponseEntity<List<Soba>> getSobePoVrsti(@PathVariable VrstaSobe vrsta) {
        List<Soba> sobe = sobaService.getSobePoVrsti(vrsta);
        return ResponseEntity.ok(sobe);
    }

    @PostMapping
    public ResponseEntity<Soba> dodajSobu(@RequestBody Soba soba) {
        return ResponseEntity.ok(sobaService.spremiSobu(soba));
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> obrisiSobu(@PathVariable Integer id) {
            sobaService.obrisiSobu(id);
        return ResponseEntity.noContent().build();
    }
}

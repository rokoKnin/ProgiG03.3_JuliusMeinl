package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.model.Soba;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/room-reservation")
public class RezervirajSobuController {

    @PostMapping
    public List<Soba> dostupneSobePoFilter(@RequestBody Map<String, LocalDate> dateMap) {
        //provjeri rezervirajSobu, ako datum odgovara, uzmi sobaId i onda provjeri po njemu u entitetu soba koja je vrsta sobe
        // za svaku vrstu napravi counter za tu vrstu



        return null;
    }
}

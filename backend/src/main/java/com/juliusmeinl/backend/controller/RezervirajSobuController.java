package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.model.Soba;
import com.juliusmeinl.backend.service.RezervirajSobuService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/reservations")
public class RezervirajSobuController {
    private final RezervirajSobuService rezervirajSobuService;

    public RezervirajSobuController(RezervirajSobuService rezervirajSobuService) {
        this.rezervirajSobuService = rezervirajSobuService;
    }

    @PostMapping
    public List<Map<String, Object>> dostupneSobe(@RequestBody Map<String, String> dateMap) {
        // moram napravit counter za svaku vrstu sobe mogucu
        LocalDate datumOd = LocalDate.parse(dateMap.get("datumOd"));
        LocalDate datumDo = LocalDate.parse(dateMap.get("datumDo"));

        return rezervirajSobuService.dostupneSobe(datumOd, datumDo);
    }
}

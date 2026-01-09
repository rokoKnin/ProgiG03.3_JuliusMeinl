package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.repository.RezervirajSobuRepository;
import com.juliusmeinl.backend.repository.SobaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RezervacijaService {
    private final SobaRepository sobaRepository;
    private final RezervirajSobuRepository rezervirajSobuRepository;

    public RezervacijaService(SobaRepository sobaRepository, RezervirajSobuRepository rezervirajSobuRepository) {
        this.sobaRepository = sobaRepository;
        this.rezervirajSobuRepository = rezervirajSobuRepository;
    }


    public List<Map<String, Object>> dostupneSobe(LocalDate datumOd, LocalDate datumDo) {

        List<Integer> zauzeteIds = rezervirajSobuRepository.findNedostupneSobeById(datumOd, datumDo);

        if (zauzeteIds.isEmpty()) {
            zauzeteIds = List.of(-1); // ako mi vrati prazan, stavljam id -1 koji nema sig da mi ne pukne kod
        }
        List<Object[]> rezultat = sobaRepository.countDostupneSobePoVrsti(zauzeteIds);

        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] r : rezultat) {
            Map<String, Object> map = new HashMap<>();
            map.put("vrsta", r[0]);
            map.put("balkon", r[1]);
            map.put("pogledNaMore", r[2]);
            map.put("brojDostupnih", r[3]);

            response.add(map);
        }
        return response;
    }


}

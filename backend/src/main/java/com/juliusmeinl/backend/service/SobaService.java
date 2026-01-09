package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.model.Soba;
import com.juliusmeinl.backend.model.VrstaSobe;
import com.juliusmeinl.backend.repository.RezervirajSobuRepository;
import com.juliusmeinl.backend.repository.SobaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class SobaService {

    private final SobaRepository sobaRepository;
    private final RezervirajSobuRepository rezervirajSobuRepository;

    public SobaService(SobaRepository sobaRepository, RezervirajSobuRepository rezervirajSobuRepository) {
        this.sobaRepository = sobaRepository;
        this.rezervirajSobuRepository = rezervirajSobuRepository;
    }

    public List<Soba> getSveSobe() {
        return sobaRepository.findAll()
                .stream()
                .sorted((s1, s2) -> s1.getBrojSobe().compareTo(s2.getBrojSobe()))
                .toList();
    }

    public Soba getSobaById(Integer id) {
        return sobaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soba s ID " + id + " nije pronađena."));
    }

    public List<Soba> getSobePoVrsti(VrstaSobe vrsta) {
        return sobaRepository.findByVrsta(vrsta);
    }

    public Soba spremiSobu(Soba soba) {
        return sobaRepository.save(soba);
    }

    public void obrisiSobu(Integer id) {
        sobaRepository.deleteById(id);
    }
    public Optional<Soba> pronadiPoId(Integer id) {
        return sobaRepository.findById(id);
    }
    public BigDecimal izracunajCijenu(VrstaSobe vrsta, int kat, boolean balkon, boolean pogledNaMore) {
        BigDecimal cijena = BigDecimal.ZERO;

        if (vrsta == VrstaSobe.DVOKREVETNA_KING || vrsta == VrstaSobe.DVOKREVETNA_TWIN) {
            if (kat == 1) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("120.00");
                else if (balkon) cijena = new BigDecimal("105.00");
                else if (pogledNaMore) cijena = new BigDecimal("110.00");
                else cijena = new BigDecimal("100.00");
            } else if (kat == 2) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("130.00");
                else if (balkon) cijena = new BigDecimal("115.00");
                else if (pogledNaMore) cijena = new BigDecimal("120.00");
                else cijena = new BigDecimal("110.00");
            } else if (kat == 3) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("140.00");
                else if (balkon) cijena = new BigDecimal("125.00");
                else if (pogledNaMore) cijena = new BigDecimal("130.00");
                else cijena = new BigDecimal("120.00");
            }
        }

        else if (vrsta == VrstaSobe.TROKREVETNA) {
            if (kat == 1) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("150.00");
                else if (balkon) cijena = new BigDecimal("135.00");
                else if (pogledNaMore) cijena = new BigDecimal("140.00");
                else cijena = new BigDecimal("130.00");
            } else if (kat == 2) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("160.00");
                else if (balkon) cijena = new BigDecimal("145.00");
                else if (pogledNaMore) cijena = new BigDecimal("150.00");
                else cijena = new BigDecimal("140.00");
            } else if (kat == 3) {
                if (balkon && pogledNaMore) cijena = new BigDecimal("170.00");
                else if (balkon) cijena = new BigDecimal("155.00");
                else if (pogledNaMore) cijena = new BigDecimal("160.00");
                else cijena = new BigDecimal("150.00");
            }
        }

        else if (vrsta == VrstaSobe.PENTHOUSE) {
            if (kat == 4) {
                cijena = new BigDecimal("250.00");
            }
        }

        return cijena;
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
            map.put("cijena", r[3]);
            map.put("brojDostupnih", r[4]);

            response.add(map);
        }
        return response;
    }

}

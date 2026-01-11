package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.model.*;
import com.juliusmeinl.backend.repository.KorisnikRepository;
import com.juliusmeinl.backend.repository.RezervacijaRepository;
import com.juliusmeinl.backend.repository.RezervirajSobuRepository;
import com.juliusmeinl.backend.repository.SobaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class RezervacijaService {
    private final SobaRepository sobaRepository;
    private final RezervirajSobuRepository rezervirajSobuRepository;
    private final RezervacijaRepository rezervacijaRepository;
    private final KorisnikRepository korisnikRepository;

    public RezervacijaService(SobaRepository sobaRepository, RezervirajSobuRepository rezervirajSobuRepository, RezervacijaRepository rezervacijaRepository, KorisnikRepository korisnikRepository) {
        this.sobaRepository = sobaRepository;
        this.rezervirajSobuRepository = rezervirajSobuRepository;
        this.rezervacijaRepository = rezervacijaRepository;
        this.korisnikRepository = korisnikRepository;
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

    public Integer kreirajRezervaciju(Integer id) {
        Optional<Korisnik> korisnik = korisnikRepository.findById(id);

        Rezervacija rezervacija = new Rezervacija();
        rezervacija.setKorisnik(korisnik.get());
        rezervacija.setIznosRezervacije(BigDecimal.ZERO);

        return rezervacijaRepository.save(rezervacija).getId();
    }

    public void rezervirajSobe(Integer rezervacijaId, List<Integer> dodijeljeneSobeId, LocalDate datumOd, LocalDate datumDo) {

        Rezervacija rezervacija = rezervacijaRepository.findById(rezervacijaId).orElseThrow(() -> new IllegalArgumentException("Rezervacija ne postoji"));

        for (Integer sobaId : dodijeljeneSobeId) {

            Soba soba = sobaRepository.findById(sobaId).orElseThrow(() -> new IllegalArgumentException("Soba ne postoji"));

            RezervirajSobu rs = new RezervirajSobu();

            RezervirajSobuId id = new RezervirajSobuId(rezervacijaId, sobaId);
            rs.setId(id);

            rs.setRezervacija(rezervacija);
            rs.setSoba(soba);
            rs.setDatumOd(datumOd);
            rs.setDatumDo(datumDo);

            rezervirajSobuRepository.save(rs);
        }
    }

}

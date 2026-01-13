package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.dto.DodatniSadrzajRequestDTO;
import com.juliusmeinl.backend.dto.RezervacijaRequestDTO;
import com.juliusmeinl.backend.dto.RezervacijaResponseDTO;
import com.juliusmeinl.backend.model.*;
import com.juliusmeinl.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class RezervacijaService {
    private final SobaRepository sobaRepository;
    private final RezervirajSobuRepository rezervirajSobuRepository;
    private final RezervacijaRepository rezervacijaRepository;
    private final KorisnikRepository korisnikRepository;
    private final DodatniSadrzajRepository dodatniSadrzajRepository;
    private final RezervirajSadrzajRepository rezervirajSadrzajRepository;

    public RezervacijaService(SobaRepository sobaRepository, RezervirajSobuRepository rezervirajSobuRepository, RezervacijaRepository rezervacijaRepository, KorisnikRepository korisnikRepository, DodatniSadrzajRepository dodatniSadrzajRepository, RezervirajSadrzajRepository rezervirajSadrzajRepository) {
        this.sobaRepository = sobaRepository;
        this.rezervirajSobuRepository = rezervirajSobuRepository;
        this.rezervacijaRepository = rezervacijaRepository;
        this.korisnikRepository = korisnikRepository;
        this.dodatniSadrzajRepository = dodatniSadrzajRepository;
        this.rezervirajSadrzajRepository = rezervirajSadrzajRepository;
    }



    public Integer kreirajRezervaciju(Integer id) {
        Optional<Korisnik> korisnik = korisnikRepository.findById(id);

        Rezervacija rezervacija = new Rezervacija();
        rezervacija.setKorisnik(korisnik.get());
        rezervacija.setIznosRezervacije(BigDecimal.ZERO);
        rezervacija.setPlaceno(true);

        return rezervacijaRepository.save(rezervacija).getId();
    }

    @Transactional
    public void rezervirajSobe(Integer rezervacijaId, List<Integer> dodijeljeneSobeId, LocalDate datumOd, LocalDate datumDo) {
        BigDecimal rezervacijaSobeCijena = BigDecimal.ZERO;
        long brojNocenja = ChronoUnit.DAYS.between(datumOd, datumDo);

        Rezervacija rezervacija = rezervacijaRepository.findById(rezervacijaId).orElseThrow(() -> new IllegalArgumentException("Rezervacija ne postoji"));

        for (Integer sobaId : dodijeljeneSobeId) {

            Soba soba = sobaRepository.findById(sobaId).orElseThrow(() -> new IllegalArgumentException("Soba ne postoji"));

            rezervacijaSobeCijena = rezervacijaSobeCijena.add(soba.getCijena().multiply(BigDecimal.valueOf(brojNocenja)));

            RezervirajSobu rs = new RezervirajSobu();

            RezervirajSobuId id = new RezervirajSobuId(rezervacijaId, sobaId);
            rs.setId(id);

            rs.setRezervacija(rezervacija);
            rs.setSoba(soba);
            rs.setDatumOd(datumOd);
            rs.setDatumDo(datumDo);

            rezervirajSobuRepository.save(rs);
        }
        rezervacija.setIznosRezervacije(rezervacijaSobeCijena);
        rezervacijaRepository.save(rezervacija);

    }

    @Transactional
    public void rezervirajSadrzaj(Integer rezervacijaId, RezervacijaRequestDTO rezervacijaRequestDTO) {
        List<DodatniSadrzajRequestDTO> dodatniSadrzajRequestDTO = rezervacijaRequestDTO.getDodatniSadrzaji();

        BigDecimal cijenaRezervacijaSadrzaj = BigDecimal.ZERO;

        Rezervacija rezervacija = rezervacijaRepository.findById(rezervacijaId).orElseThrow(() -> new IllegalArgumentException("Rezervacija ne postoji"));

        for(DodatniSadrzajRequestDTO d : dodatniSadrzajRequestDTO) {
            DodatniSadrzaj dodatniSadrzaj = dodatniSadrzajRepository.findByVrsta(d.getVrstaDodatnogSadrzaja()).orElseThrow(() -> new IllegalArgumentException("Dodatni sadrzaj ne postoji"));

            cijenaRezervacijaSadrzaj = cijenaRezervacijaSadrzaj.add(dodatniSadrzaj.getCijena());

            RezervirajSadrzaj rs = new RezervirajSadrzaj();
            rs.setRezervacija(rezervacija);
            rs.setDodatniSadrzaj(dodatniSadrzaj);

            RezervirajSadrzajId id = new RezervirajSadrzajId(rezervacijaId,dodatniSadrzaj.getId(),d.getDatum());
            rs.setId(id);

            rezervirajSadrzajRepository.save(rs);
        }
        rezervacija.setIznosRezervacije(rezervacija.getIznosRezervacije().add(cijenaRezervacijaSadrzaj));
        rezervacijaRepository.save(rezervacija);
    }


    @Transactional(readOnly = true)
    public List<Rezervacija> dohvatiSveRezervacije() {
        return rezervacijaRepository.findAll();
    }

    @Transactional
    public Rezervacija azurirajRezervaciju(Integer rezervacijaId, Rezervacija input) {
        Rezervacija rezervacija = rezervacijaRepository.findById(rezervacijaId)
                .orElseThrow(() -> new IllegalArgumentException("Rezervacija ne postoji"));

        // Update polja rezervacije
        rezervacija.setPlaceno(input.isPlaceno());
        rezervacija.setDatumRezerviranja(input.getDatumRezerviranja());

        // Update soba
        if (input.getSobe() != null) {
            rezervirajSobuRepository.deleteAll(rezervacija.getSobe());
            for (RezervirajSobu rs : input.getSobe()) {
                rs.setRezervacija(rezervacija);
                rezervirajSobuRepository.save(rs);
            }
        }

        // Update dodatni sadržaj
        if (input.getSadrzaji() != null) {
            rezervirajSadrzajRepository.deleteAll(rezervacija.getSadrzaji());
            for (RezervirajSadrzaj rs : input.getSadrzaji()) {
                rs.setRezervacija(rezervacija);
                rezervirajSadrzajRepository.save(rs);
            }
        }

        // Update ukupnog iznosa rezervacije (sobe + dodatni sadržaj)
        BigDecimal iznos = BigDecimal.ZERO;
        if (rezervacija.getSobe() != null) {
            for (RezervirajSobu rs : rezervacija.getSobe()) {
                long brojNocenja = ChronoUnit.DAYS.between(rs.getDatumOd(), rs.getDatumDo());
                iznos = iznos.add(rs.getSoba().getCijena().multiply(BigDecimal.valueOf(brojNocenja)));
            }
        }
        if (rezervacija.getSadrzaji() != null) {
            for (RezervirajSadrzaj rs : rezervacija.getSadrzaji()) {
                iznos = iznos.add(rs.getDodatniSadrzaj().getCijena());
            }
        }
        rezervacija.setIznosRezervacije(iznos);

        return rezervacijaRepository.save(rezervacija);
    }

    public List<RezervacijaResponseDTO> dohvatiSveRezervacijeDTO() {
        return rezervacijaRepository.findAllDTO();
    }
}

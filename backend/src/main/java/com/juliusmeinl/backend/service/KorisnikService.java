package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.Mjesto;
import com.juliusmeinl.backend.model.MjestoId;
import com.juliusmeinl.backend.repository.KorisnikRepository;
import com.juliusmeinl.backend.repository.MjestoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;
    private final MjestoRepository mjestoRepository;

    public KorisnikService(KorisnikRepository korisnikRepository, MjestoRepository mjestoRepository) {
        this.korisnikRepository = korisnikRepository;
        this.mjestoRepository = mjestoRepository;
    }

    @Transactional
    public Korisnik spremiKorisnika(Korisnik korisnik, MjestoId mjestoId) {
        // Dobivanje mjesta iz repozitorija
        Optional<Mjesto> mjestoOptional = mjestoRepository.findById(mjestoId);
        Mjesto mjesto = mjestoOptional.orElseGet(() -> {
            Mjesto novoMjesto = new Mjesto();
            novoMjesto.setId(mjestoId);
            return mjestoRepository.save(novoMjesto); // kreira novo mjesto ako ne postoji
        });

        korisnik.setMjesto(mjesto);

        return korisnikRepository.save(korisnik);
    }
}


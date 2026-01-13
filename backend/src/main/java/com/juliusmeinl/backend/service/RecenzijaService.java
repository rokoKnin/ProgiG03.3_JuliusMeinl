package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.dto.RecenzijaRequestDTO;
import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.Recenzija;
import com.juliusmeinl.backend.repository.KorisnikRepository;
import com.juliusmeinl.backend.repository.RecenzijaRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class RecenzijaService {

    private final RecenzijaRepository recenzijaRepository;
    private final KorisnikRepository korisnikRepository;

    public RecenzijaService(RecenzijaRepository recenzijaRepository, KorisnikRepository korisnikRepository) {
        this.recenzijaRepository = recenzijaRepository;
        this.korisnikRepository = korisnikRepository;
    }

    public void spremiRecenziju(String email, RecenzijaRequestDTO recenzijaDTO) {
        Korisnik korisnik = korisnikRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ""));

        Recenzija recenzija = new Recenzija();

        recenzija.setKorisnik(korisnik);
        recenzija.setOcjena(recenzijaDTO.getValue());
        recenzija.setKomentar(recenzijaDTO.getKomentar());

        recenzijaRepository.save(recenzija);
    }
}

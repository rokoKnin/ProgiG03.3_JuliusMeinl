package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.dto.RecenzijaRequestDTO;
import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.Recenzija;
import com.juliusmeinl.backend.repository.KorisnikRepository;
import com.juliusmeinl.backend.repository.RecenzijaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RecenzijaService {

    private final RecenzijaRepository recenzijaRepository;
    private final KorisnikRepository korisnikRepository;

    private Random random = new Random();

    public Recenzija spremiRecenziju(String email, RecenzijaRequestDTO recenzijaDTO) {
        Korisnik korisnik = korisnikRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ""));

        Recenzija recenzija = new Recenzija();

        recenzija.setKorisnik(korisnik);
        recenzija.setOcjena(recenzijaDTO.getValue());
        recenzija.setKomentar(recenzijaDTO.getKomentar());

       return recenzijaRepository.save(recenzija);
    }

    public List<Recenzija> generateRandom() {
        List<Recenzija> recenzije = recenzijaRepository.findAll();

        if (recenzije.size() <= 5) {
            return recenzije;
        } else {
            List<Recenzija> randomRecenzije = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                randomRecenzije.add(recenzije.get(random.nextInt(recenzije.size())));
            }
            return randomRecenzije;
        }
    }
}

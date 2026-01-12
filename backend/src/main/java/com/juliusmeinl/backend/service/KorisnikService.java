package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.model.*;
import com.juliusmeinl.backend.repository.DrzavaRepository;
import com.juliusmeinl.backend.repository.KorisnikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;
    private final DrzavaRepository drzavaRepository;

    @Transactional
    public Korisnik spremiKorisnika(Korisnik korisnik) {
        String imeIzJsona = korisnik.getMjesto().getDrzava().getNazivDrzave();

        Drzava drzava = drzavaRepository.findByNazivDrzave(imeIzJsona);

        korisnik.getMjesto().setDrzava(drzava);

        korisnik.setOvlast(UlogaKorisnika.REGISTRIRAN);
        return korisnikRepository.save(korisnik);
    }



    // Provjerava postojili li korisnik prema mailu
    public boolean existsByEmail(String email) {
        return korisnikRepository.existsByEmail(email);
    }

    // Dohvat korisnika po emailu
    public Optional<Korisnik> findByEmail(String email) {
        return korisnikRepository.findByEmail(email);
    }

    //Provjerava je li korisnik vlasnik
    public boolean korisnikJeVlasnik(String email) {
        Optional<Korisnik> korisnik = korisnikRepository.findByEmailAndOvlast(email, UlogaKorisnika.VLASNIK);
        return korisnik.isPresent();
    }

//    public List<String> getDeactivated() {
//        return korisnikRepository.getDeactivated();
//    }
}


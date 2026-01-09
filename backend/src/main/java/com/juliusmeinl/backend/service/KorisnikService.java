package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.model.*;
import com.juliusmeinl.backend.repository.DrzavaRepository;
import com.juliusmeinl.backend.repository.KorisnikRepository;
import com.juliusmeinl.backend.repository.MjestoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;
    private final MjestoRepository mjestoRepository;
    private final DrzavaRepository drzavaRepository;

    public KorisnikService(KorisnikRepository korisnikRepository, MjestoRepository mjestoRepository, DrzavaRepository drzavaRepository) {
        this.korisnikRepository = korisnikRepository;
        this.mjestoRepository = mjestoRepository;
        this.drzavaRepository = drzavaRepository;
    }

    public Map<String, Object> getProfileMap(Korisnik korisnik) {
        Map<String, Object> profile = new HashMap<>();
        profile.put("email", korisnik.getEmail());
        //TODO: dodati da vraca ostale stvari osim email da se moze automatski popunit dashboard

        return profile;
    }

    @Transactional
    public Korisnik spremiKorisnika(Korisnik korisnik, MjestoId mjestoId, String nazDrzava) {
        // Dobivanje mjesta iz repozitorija
        Optional<Mjesto> mjestoOptional = mjestoRepository.findById(mjestoId);

        Mjesto mjesto = new Mjesto();
        if(!mjestoOptional.isPresent()) {  //ako je novo mjesto koje nemam u bazi
            Integer drzavaId = drzavaRepository.findIdByNazivDrzave(nazDrzava);
            Drzava drzava = drzavaRepository.findById(drzavaId).get();

            mjesto.setId(mjestoId);
            mjesto.setDrzava(drzava);

            mjestoRepository.save(mjesto);
        }
        else {
            mjesto = mjestoRepository.getReferenceById(mjestoId);
        }

        korisnik.setMjesto(mjesto);

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




}

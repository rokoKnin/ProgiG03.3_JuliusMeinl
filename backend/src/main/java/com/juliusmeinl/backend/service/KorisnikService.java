package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.model.Drzava;
import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.Mjesto;
import com.juliusmeinl.backend.repository.DrzavaRepository;
import com.juliusmeinl.backend.repository.KorisnikRepository;
import com.juliusmeinl.backend.repository.MjestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;

    public KorisnikService(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    public Korisnik spremiKorisnika(Korisnik korisnik) {

        return korisnikRepository.save(korisnik);
    }
}

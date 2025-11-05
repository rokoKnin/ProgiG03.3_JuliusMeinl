package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.Mjesto;
import com.juliusmeinl.backend.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;

    @Autowired
    public KorisnikService(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    public List<Korisnik> getAllKorisnici() {
        return korisnikRepository.findAll();
    }

    public Optional<Korisnik> getKorisnikById(Integer id) {
        return korisnikRepository.findById(id);
    }

    public Korisnik saveKorisnik(Korisnik korisnik) {
        return korisnikRepository.save(korisnik);
    }

    public Korisnik updateKorisnik(Integer id, Korisnik updatedKorisnik) {
        return korisnikRepository.findById(id)
                .map(k -> {
                    k.setIme(updatedKorisnik.getIme());
                    k.setPrezime(updatedKorisnik.getPrezime());
                    k.setEmail(updatedKorisnik.getEmail());
                    k.setTelefon(updatedKorisnik.getTelefon());
                    k.setOvlast(updatedKorisnik.getOvlast());
                    k.setMjesto(updatedKorisnik.getMjesto());
                    return korisnikRepository.save(k);
                })
                .orElseThrow(() -> new RuntimeException("Korisnik not found with id: " + id));
    }

    public void deleteKorisnik(Integer id) {
        korisnikRepository.deleteById(id);
    }

    public Optional<Korisnik> findByEmail(String email) {
        return korisnikRepository.findByEmail(email);
    }

    public Optional<Korisnik> findByTelefon(String telefon) {
        return korisnikRepository.findByTelefon(telefon);
    }
    public Mjesto getDefaultMjesto() {
        Mjesto mjesto = new Mjesto();
        mjesto.setPostBr(" ");
        mjesto.setNazMjesto("");
        return mjesto;
    }
}

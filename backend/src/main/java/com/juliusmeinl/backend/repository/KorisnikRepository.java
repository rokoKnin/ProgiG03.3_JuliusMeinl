package com.juliusmeinl.backend.repository;


import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.UlogaKorisnika;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    Optional<Korisnik> findByEmail(String email);// traži korisnika po emailu

    boolean existsByEmail(String email);         // provjera postoji li korisnik po emailu

    Optional<Korisnik> findByEmailAndOvlast(String email, UlogaKorisnika ovlast);

}
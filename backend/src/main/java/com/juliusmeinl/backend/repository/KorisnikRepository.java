package com.juliusmeinl.backend.repository;


import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.UlogaKorisnika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface KorisnikRepository extends CrudRepository<Korisnik, Integer> {
    Optional<Korisnik> findByEmail(String email);// traži korisnika po emailu

    boolean existsByEmail(String email);         // provjera postoji li korisnik po emailu

    Optional<Korisnik> findByEmailAndOvlast(String email, UlogaKorisnika ovlast);

//    @Query("SELECT emailkorisnik FROM Korisnik k WHERE k.statusKor = false")
//    List<String> getDeactivated();
}
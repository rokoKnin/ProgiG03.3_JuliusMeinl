package com.juliusmeinl.backend.repository;


import com.juliusmeinl.backend.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
    Optional<Korisnik> findByEmail(String email);  //moram preko email provjerit jel postoji korisnik u bazi
}

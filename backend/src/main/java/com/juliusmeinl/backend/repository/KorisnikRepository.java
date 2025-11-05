package com.juliusmeinl.backend.repository;

import com.juliusmeinl.backend.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {

    Optional<Korisnik> findByEmail(String email);

    Optional<Korisnik> findByTelefon(String telefon);
}

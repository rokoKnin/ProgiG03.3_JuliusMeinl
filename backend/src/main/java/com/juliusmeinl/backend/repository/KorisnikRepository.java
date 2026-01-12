package com.juliusmeinl.backend.repository;


import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.UlogaKorisnika;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
    Optional<Korisnik> findByEmail(String email);// traži korisnika po emailu

    boolean existsByEmail(String email);         // provjera postoji li korisnik po emailu

    Optional<Korisnik> findByEmailAndOvlast(String email, UlogaKorisnika ovlast);

    @Modifying
    @Transactional
    @Query("UPDATE Korisnik k SET k.ovlast = :uloga WHERE k.id = :userId")
    int updateRoleOnly(@Param("userId") Integer userId, @Param("uloga") UlogaKorisnika uloga);

}
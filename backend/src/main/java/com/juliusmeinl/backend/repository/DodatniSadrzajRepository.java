package com.juliusmeinl.backend.repository;

import com.juliusmeinl.backend.model.DodatniSadrzaj;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DodatniSadrzajRepository extends JpaRepository<DodatniSadrzaj, Integer> {
    Optional<DodatniSadrzaj> findByVrstaDodatniSadrzaj(String vrsta);
}

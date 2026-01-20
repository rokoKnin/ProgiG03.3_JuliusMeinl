package com.juliusmeinl.backend.repository;

import com.juliusmeinl.backend.model.Recenzija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecenzijaRepository extends JpaRepository<Recenzija, Long> {

    @Query("SELECT AVG(r.ocjena) FROM Recenzija r")
    Double findProsjecna();
}

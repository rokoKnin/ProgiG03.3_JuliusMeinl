package com.juliusmeinl.backend.repository;

import com.juliusmeinl.backend.model.Drzava;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrzavaRepository extends JpaRepository<Drzava, Integer> {
    @Query("SELECT d.id FROM Drzava d WHERE d.nazivDrzave = :nazivDrzave")
    Integer findIdByNazivDrzave(String nazivDrzave);  //primi ime drzave vrati njen id
}

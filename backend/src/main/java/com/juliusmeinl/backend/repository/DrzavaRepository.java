package com.juliusmeinl.backend.repository;

import com.juliusmeinl.backend.model.Drzava;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrzavaRepository extends JpaRepository<Drzava, Long> {
  //  Optional<Drzava> findByNazivDrzave(String nazivDrzave);
}

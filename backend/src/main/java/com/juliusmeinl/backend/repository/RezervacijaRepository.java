package com.juliusmeinl.backend.repository;

import com.juliusmeinl.backend.model.MjestoId;
import com.juliusmeinl.backend.model.Rezervacija;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, MjestoId> {
}

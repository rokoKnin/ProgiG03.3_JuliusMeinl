package com.juliusmeinl.backend.repository;


import com.juliusmeinl.backend.dto.RezervacijaResponseDTO;
import com.juliusmeinl.backend.model.Rezervacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, Integer> {
    @Query("""
    SELECT new com.juliusmeinl.backend.dto.RezervacijaResponseDTO(
        r.id, r.datumRezerviranja, r.placeno, r.iznosRezervacije,
        k.ime, k.prezime, k.email,
        m.nazMjesto, d.nazivDrzave
    )
    FROM Rezervacija r
    JOIN r.korisnik k
    JOIN k.mjesto m
    JOIN m.drzava d
""")
    List<RezervacijaResponseDTO> findAllDTO();

}

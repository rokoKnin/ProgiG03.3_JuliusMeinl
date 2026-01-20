package com.juliusmeinl.backend.repository;


import com.juliusmeinl.backend.dto.RezervacijaResponseDTO;
import com.juliusmeinl.backend.model.Rezervacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, Integer> {
    @Query("""
SELECT DISTINCT r FROM Rezervacija r
JOIN FETCH r.korisnik k
LEFT JOIN FETCH r.sobe s
LEFT JOIN FETCH r.sadrzaji d
""")
    List<Rezervacija> findAllWithRoomsAndContents();

    List<Rezervacija> findByKorisnikIdAndDatumDoBefore(Integer korisnikId, LocalDate datum);

}

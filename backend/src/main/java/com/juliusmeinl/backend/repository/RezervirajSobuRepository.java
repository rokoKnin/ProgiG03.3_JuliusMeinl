package com.juliusmeinl.backend.repository;

import com.juliusmeinl.backend.model.RezervirajSobu;
import com.juliusmeinl.backend.model.RezervirajSobuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RezervirajSobuRepository extends JpaRepository<RezervirajSobu, RezervirajSobuId> {

    @Query("""
        SELECT DISTINCT rs.id.sobaId
        FROM RezervirajSobu rs
        WHERE NOT (rs.datumDo < :datumOd OR rs.datumOd > :datumDo)
    """)
    List<Integer> findNedostupneSobeById(LocalDate datumOd, LocalDate datumDo);

    @Query("SELECT rs FROM RezervirajSobu rs " +
            "JOIN FETCH rs.rezervacija r " +
            "JOIN FETCH r.korisnik k " +
            "JOIN FETCH k.mjesto m " +
            "JOIN FETCH m.drzava d " +
            "JOIN FETCH rs.soba s")
    List<RezervirajSobu> findAllWithDetails();
    List<RezervirajSobu> findBySobaId(Integer sobaId);
}

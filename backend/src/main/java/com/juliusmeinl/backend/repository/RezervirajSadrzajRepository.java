package com.juliusmeinl.backend.repository;

import com.juliusmeinl.backend.model.RezervirajSadrzaj;
import com.juliusmeinl.backend.model.RezervirajSadrzajId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface RezervirajSadrzajRepository extends JpaRepository<RezervirajSadrzaj, RezervirajSadrzajId> {

    // Dohvati sve rezervacije sadržaja između dva datuma rezerviranja
    List<RezervirajSadrzaj> findByRezervacija_DatumRezerviranjaBetween(LocalDate start, LocalDate end);
}

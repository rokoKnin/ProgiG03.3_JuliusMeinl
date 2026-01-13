package com.juliusmeinl.backend.repository;

import com.juliusmeinl.backend.model.Recenzija;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecenzijaRepository extends JpaRepository<Recenzija, Long> {
}

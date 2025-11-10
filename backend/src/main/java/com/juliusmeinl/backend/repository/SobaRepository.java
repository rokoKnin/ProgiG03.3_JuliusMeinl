package com.juliusmeinl.backend.repository;

import com.juliusmeinl.backend.model.Soba;
import com.juliusmeinl.backend.model.VrstaSobe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SobaRepository extends JpaRepository<Soba, Integer> {

    List<Soba> findByVrsta(VrstaSobe vrsta);
}

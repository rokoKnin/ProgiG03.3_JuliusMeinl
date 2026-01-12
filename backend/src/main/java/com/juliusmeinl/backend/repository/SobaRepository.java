package com.juliusmeinl.backend.repository;

import com.juliusmeinl.backend.model.Soba;
import com.juliusmeinl.backend.model.VrstaSobe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SobaRepository extends JpaRepository<Soba, Integer> {

    //@Query("SELECT id FROM Soba WHERE vrsta = :vrsta")
    List<Soba> findByVrsta(VrstaSobe vrsta);

    @Query("""
    SELECT s.vrsta, s.balkon, s.pogledNaMore, s.cijena, COUNT(s)
    FROM Soba s
    WHERE s.id NOT IN :zauzeteIds
    GROUP BY s.vrsta, s.balkon, s.pogledNaMore, s.cijena
    """)
    List<Object[]> countDostupneSobePoVrsti(List<Integer> zauzeteIds);

    @Query(value = """
    SELECT s.id
    FROM Soba s
    WHERE s.vrsta = :vrsta
      AND s.balkon = :balkon
      AND s.pogledNaMore = :pogledNaMore
      AND s.id NOT IN  :zauzeteIds
    """)
    List<Integer> findDostupneSobeIds(
            VrstaSobe vrsta,
            boolean balkon,
            boolean pogledNaMore,
            List<Integer> zauzeteIds
    );

}
package com.juliusmeinl.backend.repository;

import com.juliusmeinl.backend.model.Mjesto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MjestoRepository extends CrudRepository<Mjesto, Integer> {
    Optional<Mjesto> findByPostBrAndNazMjesto(String postBr, String nazMjesto);
}


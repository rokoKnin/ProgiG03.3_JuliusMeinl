package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.repository.DrzavaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DrzavaService {
    private final DrzavaRepository drzavaRepository;

    public List<String> dohvatiDrzave() {
        return drzavaRepository.findAllNazivDrzave();
    }

}

package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.model.Soba;
import com.juliusmeinl.backend.model.VrstaSobe;
import com.juliusmeinl.backend.repository.SobaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SobaService {

    private final SobaRepository sobaRepository;

    public SobaService(SobaRepository sobaRepository) {
        this.sobaRepository = sobaRepository;
    }

    public List<Soba> getSveSobe() {
        return sobaRepository.findAll();
    }

    public Soba getSobaById(Integer id) {
        return sobaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soba s ID " + id + " nije pronađena."));
    }

    public List<Soba> getSobePoVrsti(VrstaSobe vrsta) {
        return sobaRepository.findByVrsta(vrsta);
    }

    public Soba spremiSobu(Soba soba) {
        return sobaRepository.save(soba);
    }

    public void obrisiSobu(Integer id) {
        sobaRepository.deleteById(id);
    }
}

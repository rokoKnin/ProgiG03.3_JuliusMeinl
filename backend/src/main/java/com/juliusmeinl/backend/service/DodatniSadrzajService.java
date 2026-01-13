package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.dto.DodatniSadrzajResponseDTO;
import com.juliusmeinl.backend.model.DodatniSadrzaj;
import com.juliusmeinl.backend.model.StatusDodatniSadrzaj;
import com.juliusmeinl.backend.repository.DodatniSadrzajRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DodatniSadrzajService {
    private final DodatniSadrzajRepository sadrzajRepository;

    public  DodatniSadrzajService(DodatniSadrzajRepository sadrzajRepository) {
        this.sadrzajRepository = sadrzajRepository;
    }

    public List<DodatniSadrzajResponseDTO> informacijeSadrzaj() {
        List<DodatniSadrzaj> sadrzaji = sadrzajRepository.findAll();
        List<DodatniSadrzajResponseDTO> response = new ArrayList<>();

        for(DodatniSadrzaj sadrzaj : sadrzaji){
            DodatniSadrzajResponseDTO sadrzajResponseDTO = new DodatniSadrzajResponseDTO();
            sadrzajResponseDTO.setVrsta(sadrzaj.getVrsta());
            sadrzajResponseDTO.setCijena(sadrzaj.getCijena());
            sadrzajResponseDTO.setDostupan(sadrzaj.getStatus() == StatusDodatniSadrzaj.dostupan);

            response.add(sadrzajResponseDTO);
        }
        return response;
    }
}

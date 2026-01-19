package com.juliusmeinl.backend;

import com.juliusmeinl.backend.dto.RezervacijaRequestDTO;
import com.juliusmeinl.backend.dto.SobaRequestDTO;
import com.juliusmeinl.backend.model.VrstaSobe;
import com.juliusmeinl.backend.repository.RezervirajSobuRepository;
import com.juliusmeinl.backend.repository.SobaRepository;
import com.juliusmeinl.backend.service.SobaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SobaServiceTests {

    @Mock
    private SobaRepository sobaRepository;

    @Mock
    private RezervirajSobuRepository rezervirajSobuRepository;

    @InjectMocks
    private SobaService sobaService;

    @Test
    void getSobaById_SobaIdNotFound(){
        Integer sobaId = 1;

        Mockito.when(sobaRepository.findById(sobaId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sobaService.getSobaById(sobaId);
        });

        assertEquals("Soba s ID " + sobaId + " nije pronađena.", exception.getMessage());
    }

    @Test
    void dohvatiSobu_NemaDostupnih(){
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().plusDays(5);

        RezervacijaRequestDTO rezReqDTO = new RezervacijaRequestDTO();
        rezReqDTO.setDatumOd(from);
        rezReqDTO.setDatumDo(to);

        SobaRequestDTO sobaReqDTO = new SobaRequestDTO();
        sobaReqDTO.setVrsta(VrstaSobe.DVOKREVETNA_TWIN);
        sobaReqDTO.setBalkon(false);
        sobaReqDTO.setPogledNaMore(true);
        rezReqDTO.setSobe(List.of(sobaReqDTO));

        // da ne pukne kod
        Mockito.when(rezervirajSobuRepository.findNedostupneSobeById(from, to)).thenReturn(List.of(1, 2));
        // vraca prazno za dostupne
        Mockito.when(sobaRepository.findDostupneSobeIds(any(), anyBoolean(), anyBoolean(), any()))
                .thenReturn(Collections.emptyList());

        // nije dovoljno jer test prolazi i ako baci neki drugi HttpStatus npr. 404
//        assertThrows(ResponseStatusException.class, () -> {
//            sobaService.dohvatiSobe(rezReqDTO);
//        });

        assertEquals(HttpStatus.CONFLICT, assertThrows(ResponseStatusException.class, () -> {
            sobaService.dohvatiSobe(rezReqDTO);
        }).getStatusCode());
    }
}

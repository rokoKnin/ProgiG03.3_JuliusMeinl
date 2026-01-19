package com.juliusmeinl.backend;

import com.juliusmeinl.backend.dto.RecenzijaRequestDTO;
import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.Recenzija;
import com.juliusmeinl.backend.repository.*;
import com.juliusmeinl.backend.service.RecenzijaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RecenzijaServiceTests {

    @Mock
    private RecenzijaRepository recenzijaRepository;

    @Mock
    private KorisnikRepository korisnikRepository;

    @InjectMocks
    private RecenzijaService recenzijaService;

    @Test
    void spremiRecenziju_Successful(){
        String korisnikEmail = "test@gmail.com";
        Korisnik korisnik = new Korisnik();
        korisnik.setEmail(korisnikEmail);

        RecenzijaRequestDTO recReqDTO = new RecenzijaRequestDTO();
        recReqDTO.setValue(5);
        recReqDTO.setKomentar("Odlično");

        Mockito.when(korisnikRepository.findByEmail(korisnikEmail)).thenReturn(Optional.of(korisnik));
        Mockito.when(recenzijaRepository.save(any(Recenzija.class))).thenAnswer(i -> i.getArguments()[0]); // vracamo prvi spremljeni argument, u nasem slucaju jedini mockani

        Recenzija recenzija = recenzijaService.spremiRecenziju(korisnikEmail, recReqDTO);
        assertEquals(5, recenzija.getOcjena());
        assertEquals("Odlično", recenzija.getKomentar());
        assertEquals(korisnik, recenzija.getKorisnik());
    }

    @Test
    void spremiRecenziju_EmailNotFound(){
        String korisnikEmail = "test@gmail.com";
        RecenzijaRequestDTO recReqDTO = new RecenzijaRequestDTO();

        Mockito.when(korisnikRepository.findByEmail(korisnikEmail)).thenReturn(Optional.empty());

        assertEquals(HttpStatus.NOT_FOUND, assertThrows(ResponseStatusException.class, () -> {
            recenzijaService.spremiRecenziju(korisnikEmail, recReqDTO);
        }).getStatusCode());
    }
}

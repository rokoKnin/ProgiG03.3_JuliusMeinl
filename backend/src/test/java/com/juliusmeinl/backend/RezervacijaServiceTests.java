package com.juliusmeinl.backend;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.Rezervacija;
import com.juliusmeinl.backend.repository.*;
import com.juliusmeinl.backend.service.RezervacijaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RezervacijaServiceTests {

    @Mock
    private RezervacijaRepository rezervacijaRepository;

    @Mock
    private KorisnikRepository korisnikRepository;

    @InjectMocks
    private RezervacijaService rezervacijaService;

    @Test
    void kreirajRezervaciju_Successful(){
        Integer korisnikId = 1;
        Korisnik korisnik = new Korisnik();
        korisnik.setId(korisnikId);

        Integer rezervacijaId = 2;
        Rezervacija rezervacija=new Rezervacija();
        rezervacija.setId(rezervacijaId);

        Mockito.when(korisnikRepository.findById(korisnikId)).thenReturn(Optional.of(korisnik));
        Mockito.when(rezervacijaRepository.save(any(Rezervacija.class))).thenReturn(rezervacija);

        assertEquals(2, rezervacijaService.kreirajRezervaciju(korisnikId));
    }

    @Test
    void azurirajRezervaciju_RezervacijaIDNotFound(){
        Integer rezervacijaId = 1;
        Rezervacija rezervacija = new Rezervacija();

        Mockito.when(rezervacijaRepository.findById(any())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rezervacijaService.azurirajRezervaciju(rezervacijaId, rezervacija);
        });

        assertEquals("Rezervacija ne postoji", exception.getMessage());
    }
}

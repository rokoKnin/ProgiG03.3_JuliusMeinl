package com.juliusmeinl.backend;

import com.juliusmeinl.backend.model.*;
import com.juliusmeinl.backend.repository.*;
import com.juliusmeinl.backend.service.KorisnikService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class KorisnikServiceTests {

    @Mock
    private KorisnikRepository korisnikRepository;

    @Mock
    private MjestoRepository mjestoRepository;

    @Mock
    private DrzavaRepository drzavaRepository;

    @InjectMocks
    private KorisnikService korisnikService;

    @Test
    void spremiKorisnika_MjestoExistsAndAdminEmail(){
        ReflectionTestUtils.setField(korisnikService, "adminEmail", "admin@hotel.com"); // treba da bi se test mogao odraditi

        Drzava drzava = new Drzava();
        drzava.setNazivDrzave("Hrvatska");

        Mjesto mjesto = new Mjesto();
        mjesto.setNazMjesto("Cres"); //trebalo bi se staviti u lowercase to usput provjeravamo dolje
        mjesto.setPostBr("51557");
        mjesto.setDrzava(drzava);

        // simuliramo da je cres vec u bazi podataka, dakle "punimo" bazu podataka prije testiranja mjestom cres
        Mjesto bazaMjesto = new Mjesto();
        bazaMjesto.setNazMjesto("cres");
        bazaMjesto.setPostBr("51557");

        Korisnik korisnik = new Korisnik();
        korisnik.setEmail("admin@hotel.com"); // isti kao adminEmail, provjeravamo hoce li se uloga korisnika automatski postaviti na VLASNIK
        korisnik.setMjesto(mjesto);

        Mockito.when(drzavaRepository.findByNazivDrzave("Hrvatska")).thenReturn(drzava);
        Mockito.when(mjestoRepository.findByPostBrAndNazMjesto("51557", "cres")).thenReturn(Optional.of(bazaMjesto));
        Mockito.when(korisnikRepository.save(any(Korisnik.class))).thenAnswer(i -> i.getArguments()[0]); // prvo dodano u bazu

        Korisnik rez= korisnikService.spremiKorisnika(korisnik);
        assertEquals(UlogaKorisnika.VLASNIK, rez.getOvlast());
        assertEquals("cres", rez.getMjesto().getNazMjesto());
        assertEquals("51557", rez.getMjesto().getPostBr());
    }

    @Test
    void spremiKorisnika_NewMjesto(){
        ReflectionTestUtils.setField(korisnikService, "adminEmail", "admin@hotel.com"); // treba da bi se test mogao odraditi

        Drzava drzava = new Drzava();
        drzava.setNazivDrzave("Hrvatska");

        Mjesto mjesto = new Mjesto();
        mjesto.setNazMjesto("Cres");
        mjesto.setPostBr("51557");
        mjesto.setDrzava(drzava);

        Korisnik korisnik = new Korisnik();
        korisnik.setEmail("test@gmail.com");
        korisnik.setMjesto(mjesto);

        Mockito.when(drzavaRepository.findByNazivDrzave("Hrvatska")).thenReturn(drzava);
        Mockito.when(mjestoRepository.findByPostBrAndNazMjesto("51557", "cres")).thenReturn(Optional.empty());
        Mockito.when(korisnikRepository.save(any(Korisnik.class))).thenAnswer(i -> i.getArguments()[0]); // prvo dodano u bazu

        Korisnik rez= korisnikService.spremiKorisnika(korisnik);
        assertEquals(UlogaKorisnika.KORISNIK, rez.getOvlast());
        assertEquals("cres", rez.getMjesto().getNazMjesto());
        assertEquals("51557", rez.getMjesto().getPostBr());
    }

    @Test
    void updateRole_UserIdNotFound(){
        Integer korisnikId = 1;
        String novaUloga = "VLASNIK";

        Mockito.when(korisnikRepository.findById(korisnikId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            korisnikService.updateRole(korisnikId, novaUloga);
        });

        assertEquals("Korisnik s ID " + korisnikId + " ne postoji", exception.getMessage());
    }
}

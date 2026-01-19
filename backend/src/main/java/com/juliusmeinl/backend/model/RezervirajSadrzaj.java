package com.juliusmeinl.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rezervirajsadrzaj")
public class RezervirajSadrzaj {

    @EmbeddedId
    private RezervirajSadrzajId id;

    @ManyToOne
    @MapsId("rezervacijaId")
    @JoinColumn(name = "rezervacija_id")
    private Rezervacija rezervacija;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("dodatniSadrzajId")
    @JoinColumn(name = "dodatnisadrzaj_id")
    private DodatniSadrzaj dodatniSadrzaj;

    public RezervirajSadrzajId getId() {
        return id;
    }
    public void setId(RezervirajSadrzajId id) {
        this.id = id;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }
    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }

    public DodatniSadrzaj getDodatniSadrzaj() {
        return dodatniSadrzaj;
    }

    public void setDodatniSadrzaj(DodatniSadrzaj dodatniSadrzaj) {
        this.dodatniSadrzaj = dodatniSadrzaj;
    }
}


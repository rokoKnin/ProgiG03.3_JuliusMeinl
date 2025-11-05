package com.juliusmeinl.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rezervirajsadrzaj")
public class RezervirajSadrzaj {

    @EmbeddedId
    private RezervirajSadrzajId id;

    @Column(name = "datumOdSadrzaj", nullable = false)
    private LocalDate datumOd;

    @Column(name = "datumDoSadrzaj", nullable = false)
    private LocalDate datumDo;

    @Column(name = "cijena_sadrzaj", nullable = false)
    private Integer cijena;

    @ManyToOne
    @MapsId("rezervacijaId")
    @JoinColumn(name = "rezervacija_id")
    private Rezervacija rezervacija;

    @ManyToOne
    @MapsId("dodatniSadrzajId")
    @JoinColumn(name = "dodatniSadrzaj_id")
    private DodatniSadrzaj dodatniSadrzaj;

    public RezervirajSadrzajId getId() {
        return id;
    }
    public void setId(RezervirajSadrzajId id) {
        this.id = id;
    }
    public LocalDate getDatumOd() {
        return datumOd;
    }
    public void setDatumOd(LocalDate datumOd) {
        this.datumOd = datumOd;
    }
    public LocalDate getDatumDo() {
        return datumDo;
    }
    public void setDatumDo(LocalDate datumDo) {
        this.datumDo = datumDo;
    }
    public Integer getCijena() {
        return cijena;
    }
    public void setCijena(Integer cijena) {
        this.cijena = cijena;
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


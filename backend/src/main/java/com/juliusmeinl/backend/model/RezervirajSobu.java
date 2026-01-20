package com.juliusmeinl.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rezervirajsobu")
public class RezervirajSobu {

    @EmbeddedId
    private RezervirajSobuId id;

    @Column(name = "datumodsoba", nullable = false)
    private LocalDate datumOd;

    @Column(name = "datumdosoba", nullable = false)
    private LocalDate datumDo;

    @ManyToOne
    @MapsId("rezervacijaId")
    @JoinColumn(name = "rezervacija_id")
    private Rezervacija rezervacija;

    @ManyToOne
    @MapsId("sobaId")
    @JoinColumn(name = "soba_id")
    @JsonBackReference
    private Soba soba;

    public RezervirajSobuId getId() {
        return id;
    }
    public void setId(RezervirajSobuId id) {
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
    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }
    public Soba getSoba() {
        return soba;
    }
    public void setSoba(Soba soba) {
        this.soba = soba;
    }
}


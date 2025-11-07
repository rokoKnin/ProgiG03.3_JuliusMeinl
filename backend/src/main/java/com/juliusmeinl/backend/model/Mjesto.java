package com.juliusmeinl.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "mjesto")
public class Mjesto {

    @EmbeddedId
    private MjestoId id;

    @ManyToOne
    @JoinColumn(name = "drzava_id", nullable = false)
    private Drzava drzava;

    @OneToMany(mappedBy = "mjesto")
    private List<Korisnik> korisnici;

    public MjestoId getId() {
        return id;
    }

    public void setId(MjestoId id) {
        this.id = id;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }
}

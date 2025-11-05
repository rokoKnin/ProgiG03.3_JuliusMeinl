package com.juliusmeinl.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "upit")
public class Upit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upit_id")
    private Integer id;

    @Column(name = "datumUpit", nullable = false)
    private LocalDateTime datumUpit;

    @Column(name = "porukaUpit", nullable = false, columnDefinition = "TEXT")
    private String poruka;

    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public LocalDateTime getDatumUpit() {
        return datumUpit;
    }
    public void setDatumUpit(LocalDateTime datumUpit) {
        this.datumUpit = datumUpit;
    }
    public String getPoruka() {
        return poruka;
    }
    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
    public Korisnik getKorisnik() {
        return korisnik;
    }
    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

}

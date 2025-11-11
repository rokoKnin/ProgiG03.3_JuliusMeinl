package com.juliusmeinl.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "recenzija")
public class Recenzija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recenzija_id")
    private Integer id;

    @Column(name = "ocjenarecenzija", nullable = false)
    private Integer ocjena;

    @Column(name = "komentarrecenzija", nullable = false, columnDefinition = "TEXT")
    private String komentar;

    @Column(name = "datumrecenzija", nullable = false)
    private LocalDate datum;

    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getOcjena() {
        return ocjena;
    }
    public void setOcjena(Integer ocjena) {
        this.ocjena = ocjena;
    }
    public String getKomentar() {
        return komentar;
    }
    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
    public LocalDate getDatum() {
        return datum;
    }
    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
    public Korisnik getKorisnik() {
        return korisnik;
    }
    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }



}

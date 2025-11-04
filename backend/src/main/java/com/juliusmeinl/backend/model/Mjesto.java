package com.juliusmeinl.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "mjesto")
public class Mjesto {
    @Id
    @Column(name = "postBr", length = 20)
    private String postBr;

    @Column(name = "nazMjesto", nullable = false, length = 50)
    private String nazMjesto;

    @ManyToOne
    @JoinColumn(name = "drzava_id", nullable = false)
    private Drzava drzava;

    @OneToMany(mappedBy = "mjesto")
    private List<Korisnik> korisnici;

    public String getPostBr() {
        return postBr;
    }
    public void setPostBr(String postBr) {
        this.postBr = postBr;
    }
    public String getNazMjesto() {
        return nazMjesto;
    }
    public void setNazMjesto(String nazMjesto) {
        this.nazMjesto = nazMjesto;
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

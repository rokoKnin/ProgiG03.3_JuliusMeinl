package com.juliusmeinl.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "korisnik")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "korisnik_id")
    private Integer id;

    @Column(name = "imekorisnik", nullable = false, length = 50)
    private String ime;

    @Column(name = "prezimekorisnik", nullable = false, length = 50)
    private String prezime;

    @Column(name = "emailkorisnik", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "telefonkorisnik", nullable = false, unique = true, length = 20)
    private String telefon;

    @Column(name = "ovlastkorisnik", nullable = false, length = 20)
    private String ovlast;


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "postbr", referencedColumnName = "postbr", nullable = false),
            @JoinColumn(name = "nazmjesto", referencedColumnName = "nazmjesto", nullable = false)
    })
    private Mjesto mjesto;

    @OneToMany(mappedBy = "korisnik")
    private List<Rezervacija> rezervacije;

    @OneToMany(mappedBy = "korisnik")
    private List<Upit> upiti;

    @OneToMany(mappedBy = "korisnik")
    private List<Recenzija> recenzije;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getIme() {
        return ime;
    }
    public void setIme(String ime) {
        this.ime = ime;
    }
    public String getPrezime() {
        return prezime;
    }
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefon() {
        return telefon;
    }
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
    public String getOvlast() {
        return ovlast;
    }
    public void setOvlast(String ovlast) {
        this.ovlast = ovlast;
    }
    public Mjesto getMjesto() {
        return mjesto;
    }
    public void setMjesto(Mjesto mjesto) {
        this.mjesto = mjesto;
    }




    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }
    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }
    public List<Upit> getUpiti() {
        return upiti;
    }
    public void setUpiti(List<Upit> upiti) {
        this.upiti = upiti;
    }
    public List<Recenzija> getRecenzije() {
        return recenzije;
    }
    public void setRecenzije(List<Recenzija> recenzije) {
        this.recenzije = recenzije;
    }

}

package com.juliusmeinl.backend.dto;

import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
public class RezervacijaResponseDTO {
    private Integer id;
    private LocalDate datumRezerviranja;
    private boolean placeno;
    private BigDecimal iznos;

    private String ime;
    private String prezime;
    private String email;

    private String mjesto;
    private String drzava;

    public RezervacijaResponseDTO(Integer id, LocalDate datumRezerviranja, boolean placeno, BigDecimal iznos,
                                  String imekorisnik, String prezimekorisnik, String emailkorisnik,
                                  String nazmjesto, String nazivdrzave) {
        this.id = id;
        this.datumRezerviranja = datumRezerviranja;
        this.placeno = placeno;
        this.iznos = iznos;
        this.ime = imekorisnik;
        this.prezime = prezimekorisnik;
        this.email = emailkorisnik;
        this.mjesto = nazmjesto;
        this.drzava = nazivdrzave;
    }


    // Getteri i setteri (možeš generirati IDE-om)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDate getDatumRezerviranja() { return datumRezerviranja; }
    public void setDatumRezerviranja(LocalDate datumRezerviranja) { this.datumRezerviranja = datumRezerviranja; }
    public boolean isPlaceno() { return placeno; }
    public void setPlaceno(boolean placeno) { this.placeno = placeno; }
    public BigDecimal getIznos() { return iznos; }
    public void setIznos(BigDecimal iznos) { this.iznos = iznos; }
    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }
    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMjesto() { return mjesto; }
    public void setMjesto(String mjesto) { this.mjesto = mjesto; }
    public String getDrzava() { return drzava; }
    public void setDrzava(String drzava) { this.drzava = drzava; }
}

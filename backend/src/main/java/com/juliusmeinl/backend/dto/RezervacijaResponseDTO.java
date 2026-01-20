package com.juliusmeinl.backend.dto;

import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class RezervacijaResponseDTO {
    private Integer id;
    private LocalDate datumRezerviranja;
    private boolean placeno;
    private BigDecimal iznos;

    private String ime;
    private String prezime;
    private String email;
   // private Integer korisnikId;

    private List<SobaDTO> sobe = new ArrayList<>();
    private List<DodatakDTO> sadrzaji = new ArrayList<>();

    public RezervacijaResponseDTO(Integer id, LocalDate datumRezerviranja, boolean placeno, BigDecimal iznos,
                                  String ime, String prezime, String email/*, Integer korisnikId*/) {
        this.id = id;
        this.datumRezerviranja = datumRezerviranja;
        this.placeno = placeno;
        this.iznos = iznos;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        //this.korisnikId = korisnikId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDatumRezerviranja() {
        return datumRezerviranja;
    }

    public void setDatumRezerviranja(LocalDate datumRezerviranja) {
        this.datumRezerviranja = datumRezerviranja;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public void setIznos(BigDecimal iznos) {
        this.iznos = iznos;
    }

    public boolean isPlaceno() {
        return placeno;
    }

    public void setPlaceno(boolean placeno) {
        this.placeno = placeno;
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

//    public Integer getKorisnikId() { return korisnikId; }

//    public void setKorisnikId(Integer korisnikId) { this.korisnikId = korisnikId; }

    public List<SobaDTO> getSobe() {
        return sobe;
    }

    public void setSobe(List<SobaDTO> sobe) {
        this.sobe = sobe;
    }

    public List<DodatakDTO> getSadrzaji() {
        return sadrzaji;
    }

    public void setSadrzaji(List<DodatakDTO> sadrzaji) {
        this.sadrzaji = sadrzaji;
    }
}

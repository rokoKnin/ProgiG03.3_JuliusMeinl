package com.juliusmeinl.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "rezervacija")
public class Rezervacija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rezervacija_id")
    private Integer id;

    @Column(name = "datumrezerviranja", nullable = false)
    private LocalDate datumRezerviranja = LocalDate.now();

    @Column(name = "placeno", nullable = false)
    private boolean placeno = false;

    @Column(name = "iznos_rezervacije", nullable = false)
    private BigDecimal iznosRezervacije;

    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;

    @OneToMany(mappedBy = "rezervacija")
    private List<RezervirajSadrzaj> sadrzaji;

    @OneToMany(mappedBy = "rezervacija")
    private List<RezervirajSobu> sobe;

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
    public boolean isPlaceno() {
        return placeno;
    }
    public void setPlaceno(boolean placeno) {
        this.placeno = placeno;
    }
    public Korisnik getKorisnik() {
        return korisnik;
    }
    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public BigDecimal getIznosRezervacije() {
        return iznosRezervacije;
    }

    public void setIznosRezervacije(BigDecimal iznosRezervacije) {
        this.iznosRezervacije = iznosRezervacije;
    }

    public List<RezervirajSadrzaj> getSadrzaji() {
        return sadrzaji;
    }

    public void setSadrzaji(List<RezervirajSadrzaj> sadrzaji) {
        this.sadrzaji = sadrzaji;
    }

    public List<RezervirajSobu> getSobe() {
        return sobe;
    }

    public void setSobe(List<RezervirajSobu> sobe) {
        this.sobe = sobe;
    }
}

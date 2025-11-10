package com.juliusmeinl.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "soba")
public class Soba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soba_id")
    private Integer id;

    @Column(name = "broj_sobe", nullable = false, unique = true, length = 5)
    private String brojSobe;

    @Column(name = "kat", nullable = false)
    private Integer kat;

    @Enumerated(EnumType.STRING)
    @Column(name = "vrsta", nullable = false)
    private VrstaSobe vrsta;

    @Column(name = "kapacitet", nullable = false)
    private Integer kapacitet;

    @Column(name = "balkon", nullable = false)
    private Boolean balkon;

    @Column(name = "pogled_na_more", nullable = false)
    private Boolean pogledNaMore = false;

    @Column(name = "cijena", nullable = false)
    private BigDecimal cijena;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSobe status;

    @OneToMany(mappedBy = "soba")
    private List<RezervirajSobu> rezervacijeSoba;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrojSobe() {
        return brojSobe;
    }
    public void setBrojSobe(String brojSobe) {
        this.brojSobe = brojSobe;
    }

    public Integer getKat() {return kat;}
    public void setKat(Integer kat) {this.kat = kat;}

    public VrstaSobe getVrsta() {return vrsta;}
    public void setVrsta(VrstaSobe vrsta) {this.vrsta = vrsta;}

    public Integer getKapacitet() {return kapacitet;}
    public void setKapacitet(Integer kapacitet) {this.kapacitet = kapacitet;}

    public Boolean getBalkon() { return balkon; }
    public void setBalkon(Boolean balkon) {this.balkon = balkon;}

    public Boolean getPogledNaMore() {return pogledNaMore;}
    public void setPogledNaMore(Boolean pogledNaMore) { this.pogledNaMore = pogledNaMore; }

    public BigDecimal getCijena() {
        return cijena;
    }
    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }

    public StatusSobe getStatus() {
        return status;
    }
    public void setStatus(StatusSobe status) {
        this.status = status;
    }


    public List<RezervirajSobu> getRezervacijeSoba() {
        return rezervacijeSoba;
    }
    public void setRezervacijeSoba(List<RezervirajSobu> rezervacijeSoba) {
        this.rezervacijeSoba = rezervacijeSoba;
    }
}

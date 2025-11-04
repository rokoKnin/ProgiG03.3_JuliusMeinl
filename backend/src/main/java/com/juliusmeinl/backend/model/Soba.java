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

    @Column(name = "vrsta", nullable = false, length = 20)
    private String vrsta;

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
    public String getVrsta() {
        return vrsta;
    }
    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }
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

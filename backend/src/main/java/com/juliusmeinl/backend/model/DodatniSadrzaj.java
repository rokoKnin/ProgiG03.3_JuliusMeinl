package com.juliusmeinl.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "dodatnisadržaj")
public class DodatniSadrzaj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dodatniSadrzaj_id")
    private Integer id;

    @Column(name = "vrstaDodatniSadrzaj", nullable = false, length = 20)
    private String vrsta;

    @Column(name = "statusDodatniSadrzaj", nullable = false, length = 20)
    private String status;

    @Column(name = "kapacitetDodatniSadrzaj", nullable = false)
    private Integer kapacitet;

    @OneToMany(mappedBy = "dodatniSadrzaj")
    private List<RezervirajSadrzaj> rezervacijeSadrzaja;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getVrsta() {
        return vrsta;
    }
    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getKapacitet() {
        return kapacitet;
    }
    public void setKapacitet(Integer kapacitet) {
        this.kapacitet = kapacitet;
    }

    public List<RezervirajSadrzaj> getRezervacijeSadrzaja() {
        return rezervacijeSadrzaja;
    }

    public void setRezervacijeSadrzaja(List<RezervirajSadrzaj> rezervacijeSadrzaja) {
        this.rezervacijeSadrzaja = rezervacijeSadrzaja;
    }
}

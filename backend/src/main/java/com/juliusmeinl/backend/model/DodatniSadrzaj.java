package com.juliusmeinl.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "dodatnisadrzaj")
public class DodatniSadrzaj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dodatnisadrzaj_id")
    private Integer id;

    @Column(name = "vrstadodatnisadrzaj", nullable = false, length = 20)
    private String vrsta;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusdodatnisadrzaj", nullable = false, length = 20)
    private StatusDodatniSadrzaj status;

    @Column(name = "cijena_sadrzaj", nullable = false)
    private BigDecimal cijena;

    @JsonIgnore
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
    public StatusDodatniSadrzaj getStatus() {
        return status;
    }
    public void setStatus(StatusDodatniSadrzaj status) {
        this.status = status;
    }
    public BigDecimal getCijena() {
        return cijena;
    }
    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }

    public List<RezervirajSadrzaj> getRezervacijeSadrzaja() {
        return rezervacijeSadrzaja;
    }

    public void setRezervacijeSadrzaja(List<RezervirajSadrzaj> rezervacijeSadrzaja) {
        this.rezervacijeSadrzaja = rezervacijeSadrzaja;
    }
}

package com.juliusmeinl.backend.dto;

import java.math.BigDecimal;

public class DodatniSadrzajResponseDTO {

    private String vrsta;
    private boolean dostupan;
    private BigDecimal cijena;

    public String getVrsta() {
        return vrsta;
    }
    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }
    public boolean isDostupan() {
        return dostupan;
    }
    public void setDostupan(boolean dostupan) {
        this.dostupan = dostupan;
    }
    public BigDecimal getCijena() {
        return cijena;
    }
    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }
}


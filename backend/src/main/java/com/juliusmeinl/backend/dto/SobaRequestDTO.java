package com.juliusmeinl.backend.dto;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SobaRequestDTO {

    private String vrsta;
    private Boolean balkon;
    private Boolean pogledNaMore;
    private BigDecimal cijena;
    private Integer brojDostupnih;

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public Boolean getBalkon() {
        return balkon;
    }

    public void setBalkon(Boolean balkon) {
        this.balkon = balkon;
    }

    public Boolean getPogledNaMore() {
        return pogledNaMore;
    }

    public void setPogledNaMore(Boolean pogledNaMore) {
        this.pogledNaMore = pogledNaMore;
    }

    public BigDecimal getCijena() {
        return cijena;
    }
    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }
    public Integer getBrojDostupnih() {
        return brojDostupnih;
    }
    public void setBrojDostupnih(Integer brojDostupnih) {
        this.brojDostupnih = brojDostupnih;
    }


}


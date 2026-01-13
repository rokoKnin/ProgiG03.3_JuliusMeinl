package com.juliusmeinl.backend.dto;

import com.juliusmeinl.backend.model.VrstaSobe;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SobaRequestDTO {

    private VrstaSobe vrsta;
    private Integer brojDostupnih;
    private Boolean pogledNaMore;
    private Boolean balkon;
    private BigDecimal cijena;

    public VrstaSobe getVrsta() {
        return vrsta;
    }
    public void setVrsta(VrstaSobe vrsta) {
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


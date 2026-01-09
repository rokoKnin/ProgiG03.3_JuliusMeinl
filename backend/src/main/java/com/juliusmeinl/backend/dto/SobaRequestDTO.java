package com.juliusmeinl.backend.dto;

import java.time.LocalDate;

public class SobaRequestDTO {

    private String vrstaSobe;
    private Boolean balkon;
    private Boolean pogledNaMore;
    private LocalDate datumOd;
    private LocalDate datumDo;

    public String getVrstaSobe() {
        return vrstaSobe;
    }

    public void setVrstaSobe(String vrstaSobe) {
        this.vrstaSobe = vrstaSobe;
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

    public LocalDate getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(LocalDate datumOd) {
        this.datumOd = datumOd;
    }

    public LocalDate getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(LocalDate datumDo) {
        this.datumDo = datumDo;
    }
}


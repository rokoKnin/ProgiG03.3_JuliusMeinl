package com.juliusmeinl.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RezervacijaRequestDTO {

    private LocalDate datumOd;
    private LocalDate datumDo;
    private BigDecimal cijena;
    @JsonProperty("odabraneSobe")
    private List<SobaRequestDTO> sobe;
    @JsonProperty("odabraniDodatniSadrzaj")
    private List<DodatniSadrzajRequestDTO> dodatniSadrzaji;



    public List<SobaRequestDTO> getSobe() {
        return sobe;
    }

    public void setSobe(List<SobaRequestDTO> sobe) {
        this.sobe = sobe;
    }

    public List<DodatniSadrzajRequestDTO> getDodatniSadrzaji() {
        return dodatniSadrzaji;
    }

    public void setDodatniSadrzaji(List<DodatniSadrzajRequestDTO> dodatniSadrzaji) {
        this.dodatniSadrzaji = dodatniSadrzaji;
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
    public BigDecimal getCijena() {
        return cijena;
    }
    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }

}


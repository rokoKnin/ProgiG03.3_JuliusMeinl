package com.juliusmeinl.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class RezervacijaRequestDTO {

    private LocalDate datumOd;  //provjeriti jel ime oke, drugacije je od onog u sobaController
    private LocalDate datumDo;
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
}


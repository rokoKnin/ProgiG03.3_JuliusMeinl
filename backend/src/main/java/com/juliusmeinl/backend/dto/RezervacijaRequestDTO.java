package com.juliusmeinl.backend.dto;

import java.time.LocalDate;
import java.util.List;

public class RezervacijaRequestDTO {

    private LocalDate datumSobeOd;  //provjeriti jel ime oke, drugacije je od onog u sobaController
    private LocalDate datumSobeDo;
    private List<SobaRequestDTO> sobe;
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
    public LocalDate getDatumSobeOd() {
        return datumSobeOd;
    }
    public void setDatumSobeOd(LocalDate datumSobeOd) {
        this.datumSobeOd = datumSobeOd;
    }
    public LocalDate getDatumSobeDo() {
        return datumSobeDo;
    }
    public void setDatumSobeDo(LocalDate datumSobeDo) {
        this.datumSobeDo = datumSobeDo;
    }
}


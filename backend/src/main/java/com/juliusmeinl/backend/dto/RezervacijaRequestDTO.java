package com.juliusmeinl.backend.dto;

import java.util.List;

public class RezervacijaRequestDTO {

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
}


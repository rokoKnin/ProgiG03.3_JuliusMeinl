package com.juliusmeinl.backend.dto;

import java.util.List;

public class ProfilResponseDTO {
    private List<RezervacijaRequestDTO> rezervacije;
    private List<SobaRequestDTO> sobeProfil;
    private List<DodatniSadrzajRequestDTO> dodatniSadrzajiProfil;

    public List<RezervacijaRequestDTO> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(List<RezervacijaRequestDTO> rezervacije) {
        this.rezervacije = rezervacije;
    }

    public void setSobeProfil(List<SobaRequestDTO> sobeProfil) {
        this.sobeProfil = sobeProfil;
    }

    public List<SobaRequestDTO> getSobeProfil() {
        return sobeProfil;
    }

    public List<DodatniSadrzajRequestDTO> getDodatniSadrzajiProfil() {
        return dodatniSadrzajiProfil;
    }

    public void setDodatniSadrzajiProfil(List<DodatniSadrzajRequestDTO> dodatniSadrzajiProfil) {
        this.dodatniSadrzajiProfil = dodatniSadrzajiProfil;
    }
}

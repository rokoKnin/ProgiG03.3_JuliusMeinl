package com.juliusmeinl.backend.dto;

import java.time.LocalDate;

public class DodatakDTO {
    private Integer contentId;
    private String vrsta;       // naziv dodatnog sadržaja
    private LocalDate datum;    // datum rezervacije dodatnog sadržaja

    public DodatakDTO(Integer contentId, String vrsta, LocalDate datum) {
        this.contentId = contentId;
        this.vrsta = vrsta;
        this.datum = datum;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
}

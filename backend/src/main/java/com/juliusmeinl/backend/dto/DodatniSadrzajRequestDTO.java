package com.juliusmeinl.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DodatniSadrzajRequestDTO {

    private String vrstaDodatnogSadrzaja;
    private LocalDate datum;
    private BigDecimal cijena;

    public String getVrstaDodatnogSadrzaja() {
        return vrstaDodatnogSadrzaja;
    }

    public void setVrstaDodatnogSadrzaja(String vrstaDodatnogSadrzaja) {
        this.vrstaDodatnogSadrzaja = vrstaDodatnogSadrzaja;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public BigDecimal getCijena() {
        return cijena;
    }
    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }
}


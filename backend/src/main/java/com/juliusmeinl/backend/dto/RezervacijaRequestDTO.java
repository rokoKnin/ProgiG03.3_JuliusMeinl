package com.juliusmeinl.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.juliusmeinl.backend.model.RezervirajSadrzaj;
import com.juliusmeinl.backend.model.RezervirajSobu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RezervacijaRequestDTO {

    private LocalDate datumOd;
    private LocalDate datumDo;
    private BigDecimal cijena;
    @JsonProperty("odabraneSobe")
    private List<SobaRequestDTO> sobe;
    @JsonProperty("odabraniDodatniSadrzaj")
    private List<DodatniSadrzajRequestDTO> dodatniSadrzaji;

    public String printForEmail() {
        StringBuilder ispis = new StringBuilder();
        if (this.getSobe() != null && !this.getSobe().isEmpty()) {
            for (SobaRequestDTO soba : this.getSobe()) {
                ispis.append("Rezervacija sobe:\n  ")
                        .append(soba.getVrsta())
                        .append("\n  Vrijeme rezervacije: ")
                        .append(this.getDatumOd())
                        .append(" - ")
                        .append(this.getDatumDo());
            }
        } else if (this.getDodatniSadrzaji() != null && !this.getDodatniSadrzaji().isEmpty()) {
            for (DodatniSadrzajRequestDTO sadrzaj : this.getDodatniSadrzaji()) {
                ispis.append("Rezervacija sadrzaja:\n  ")
                        .append(sadrzaj.getVrstaDodatnogSadrzaja())
                        .append("\n  Vrijeme rezervacije: ")
                        .append(sadrzaj.getDatum())
                        .append("\n");
            }
        }
        return ispis.toString();
    }
}


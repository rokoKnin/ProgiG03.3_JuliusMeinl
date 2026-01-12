package com.juliusmeinl.backend.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class RezervirajSadrzajId implements Serializable {

    private Integer rezervacijaId;
    private Integer dodatniSadrzajId;
    private LocalDate datumSadrzaj;

    public RezervirajSadrzajId() {}

    public RezervirajSadrzajId(Integer rezervacijaId, Integer dodatniSadrzajId, LocalDate datumSadrzaj) {
        this.rezervacijaId = rezervacijaId;
        this.dodatniSadrzajId = dodatniSadrzajId;
        this.datumSadrzaj = datumSadrzaj;
    }

    public Integer getRezervacijaId() {
        return rezervacijaId;
    }

    public void setRezervacijaId(Integer rezervacijaId) {
        this.rezervacijaId = rezervacijaId;
    }

    public Integer getDodatniSadrzajId() {
        return dodatniSadrzajId;
    }

    public void setDodatniSadrzajId(Integer dodatniSadrzajId) {
        this.dodatniSadrzajId = dodatniSadrzajId;
    }

    public LocalDate getDatumSadrzaj() {
        return datumSadrzaj;
    }
    public void setDatumSadrzaj(LocalDate datumSadrzaj) {
        this.datumSadrzaj = datumSadrzaj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RezervirajSadrzajId)) return false;
        RezervirajSadrzajId that = (RezervirajSadrzajId) o;
        return Objects.equals(rezervacijaId, that.rezervacijaId) &&
                Objects.equals(dodatniSadrzajId, that.dodatniSadrzajId) &&
                Objects.equals(datumSadrzaj, that.datumSadrzaj);
    }



    @Override
    public int hashCode() {
        return Objects.hash(rezervacijaId, dodatniSadrzajId, datumSadrzaj);
    }
}


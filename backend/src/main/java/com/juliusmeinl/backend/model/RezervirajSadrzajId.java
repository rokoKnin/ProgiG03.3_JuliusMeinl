package com.juliusmeinl.backend.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RezervirajSadrzajId implements Serializable {

    private Integer rezervacijaId;
    private Integer dodatniSadrzajId;

    public RezervirajSadrzajId() {}

    public RezervirajSadrzajId(Integer rezervacijaId, Integer dodatniSadrzajId) {
        this.rezervacijaId = rezervacijaId;
        this.dodatniSadrzajId = dodatniSadrzajId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RezervirajSadrzajId)) return false;
        RezervirajSadrzajId that = (RezervirajSadrzajId) o;
        return Objects.equals(rezervacijaId, that.rezervacijaId) &&
                Objects.equals(dodatniSadrzajId, that.dodatniSadrzajId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rezervacijaId, dodatniSadrzajId);
    }
}


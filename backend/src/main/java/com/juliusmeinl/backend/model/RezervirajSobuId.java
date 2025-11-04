package com.juliusmeinl.backend.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RezervirajSobuId implements Serializable {

    private Integer rezervacijaId;
    private Integer sobaId;

    public RezervirajSobuId() {}

    public RezervirajSobuId(Integer rezervacijaId, Integer sobaId) {
        this.rezervacijaId = rezervacijaId;
        this.sobaId = sobaId;
    }

    public Integer getRezervacijaId() {
        return rezervacijaId;
    }

    public void setRezervacijaId(Integer rezervacijaId) {
        this.rezervacijaId = rezervacijaId;
    }

    public Integer getSobaId() {
        return sobaId;
    }

    public void setSobaId(Integer sobaId) {
        this.sobaId = sobaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RezervirajSobuId)) return false;
        RezervirajSobuId that = (RezervirajSobuId) o;
        return Objects.equals(rezervacijaId, that.rezervacijaId) &&
                Objects.equals(sobaId, that.sobaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rezervacijaId, sobaId);
    }
}

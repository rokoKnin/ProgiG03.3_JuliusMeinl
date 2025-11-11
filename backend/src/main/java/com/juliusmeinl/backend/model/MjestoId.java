package com.juliusmeinl.backend.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MjestoId implements Serializable {

    @Column(name = "postbr", length = 20)
    private String postBr;

    @Column(name = "nazmjesto", length = 50)
    private String nazMjesto;

    public MjestoId() {}

    public MjestoId(String postBr, String nazMjesto) {
        this.postBr = postBr;
        this.nazMjesto = nazMjesto;
    }

    public String getPostBr() {
        return postBr;
    }

    public void setPostBr(String postBr) {
        this.postBr = postBr;
    }

    public String getNazMjesto() {
        return nazMjesto;
    }

    public void setNazMjesto(String nazMjesto) {
        this.nazMjesto = nazMjesto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MjestoId)) return false;
        MjestoId that = (MjestoId) o;
        return Objects.equals(postBr, that.postBr) &&
                Objects.equals(nazMjesto, that.nazMjesto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postBr, nazMjesto);
    }
}

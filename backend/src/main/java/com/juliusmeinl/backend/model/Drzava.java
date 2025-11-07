package com.juliusmeinl.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "drzava")
public class Drzava {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drzava_id")
    private Integer id;

    @Column(name = "nazivdrzave", nullable = false, unique = true, length = 50)
    private String nazivDrzave;

    @OneToMany(mappedBy = "drzava")
    private List<Mjesto> mjesta;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNazivDrzave() {
        return nazivDrzave;
    }
    public void setNazivDrzave(String nazivDrzave) {
        this.nazivDrzave = nazivDrzave;
    }
    public List<Mjesto> getMjesta() {
        return mjesta;
    }
    public void setMjesta(List<Mjesto> mjesta) {
        this.mjesta = mjesta;
    }
}

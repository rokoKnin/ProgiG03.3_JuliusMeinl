package com.juliusmeinl.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "korisnik")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "korisnik_id")
    private Integer id;

    @Column(name = "imekorisnik", nullable = false, length = 50)
    private String ime;

    @Column(name = "prezimekorisnik", nullable = false, length = 50)
    private String prezime;

    @Column(name = "emailkorisnik", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "telefonkorisnik", unique = true, length = 20)
    private String telefon;

    @Column(name = "ovlastkorisnik", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private UlogaKorisnika ovlast;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "mjesto_id",referencedColumnName = "id", nullable = false)
    private Mjesto mjesto;

    @JsonIgnore
    @OneToMany(mappedBy = "korisnik")
    private List<Rezervacija> rezervacije;

    @JsonIgnore
    @OneToMany(mappedBy = "korisnik")
    private List<Upit> upiti;

    @JsonIgnore
    @OneToMany(mappedBy = "korisnik")
    private List<Recenzija> recenzije;
}

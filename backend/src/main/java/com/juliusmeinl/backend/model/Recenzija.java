package com.juliusmeinl.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recenzija")
public class Recenzija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recenzija_id")
    private Integer id;

    @Column(name = "ocjenarecenzija", nullable = false)
    private Integer ocjena;

    @Column(name = "komentarrecenzija", nullable = false, columnDefinition = "TEXT")
    private String komentar;

    @Column(name = "datumrecenzija", nullable = false)
    private LocalDate datum = LocalDate.now();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;
}

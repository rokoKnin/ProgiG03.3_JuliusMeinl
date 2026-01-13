package com.juliusmeinl.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "soba")
public class Soba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soba_id")
    private Integer id;

    @Column(name = "broj_sobe", nullable = false, unique = true, length = 5)
    private String brojSobe;

    @Column(name = "kat", nullable = false)
    private Integer kat;

    @Enumerated(EnumType.STRING)
    @Column(name = "vrsta", nullable = false)
    private VrstaSobe vrsta;

    @Column(name = "kapacitet", nullable = false)
    private Integer kapacitet;

    @Column(name = "balkon", nullable = false)
    private Boolean balkon;

    @Column(name = "pogled_na_more", nullable = false)
    private Boolean pogledNaMore = false;

    @Column(name = "cijena", nullable = false)
    private BigDecimal cijena;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSobe status;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "soba", fetch = FetchType.LAZY)
    private List<RezervirajSobu> rezervacijeSoba;
}

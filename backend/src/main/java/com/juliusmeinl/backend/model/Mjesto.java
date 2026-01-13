package com.juliusmeinl.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mjesto")
public class Mjesto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name="nazmjesto",nullable = false)
    private String nazMjesto;

    @Column(name="postbr",nullable = false)
    private String postBr;

    @ToString.Exclude
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "drzava_id", nullable = false)
    private Drzava drzava;

    @OneToMany(mappedBy = "mjesto")
    private List<Korisnik> korisnici;

}

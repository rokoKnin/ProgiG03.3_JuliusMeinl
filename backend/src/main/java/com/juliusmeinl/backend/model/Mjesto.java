package com.juliusmeinl.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ToString.Exclude
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "drzava_id", nullable = false)
    private Drzava drzava;

    @ToString.Exclude
    @JsonIgnoreProperties({"korisnici"})
    @OneToMany(mappedBy = "mjesto")
    private List<Korisnik> korisnici;

}

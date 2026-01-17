package com.juliusmeinl.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drzava")
public class Drzava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drzava_id")
    private Integer id;

    @Column(name = "nazivdrzave", nullable = false, unique = true, length = 50)
    private String nazivDrzave;

    @ToString.Exclude
    @JsonIgnoreProperties({"mjesta"})
    @OneToMany(mappedBy = "drzava")
    private List<Mjesto> mjesta;
}

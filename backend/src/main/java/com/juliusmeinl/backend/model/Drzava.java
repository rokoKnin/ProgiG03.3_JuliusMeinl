package com.juliusmeinl.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "drzava")
    private List<Mjesto> mjesta;
}

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
@Table(name = "rezervirajsadrzaj")
public class RezervirajSadrzaj {

    @EmbeddedId
    @JsonIgnore
    private RezervirajSadrzajId id;

    @JsonIgnore
    @ManyToOne
    @MapsId("rezervacijaId")
    @JoinColumn(name = "rezervacija_id")
    private Rezervacija rezervacija;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("dodatniSadrzajId")
    @JoinColumn(name = "dodatnisadrzaj_id")
    private DodatniSadrzaj dodatniSadrzaj;
}


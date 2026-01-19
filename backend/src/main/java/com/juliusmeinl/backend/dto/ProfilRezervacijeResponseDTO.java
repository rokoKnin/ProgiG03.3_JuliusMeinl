package com.juliusmeinl.backend.dto;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.RezervirajSadrzaj;
import com.juliusmeinl.backend.model.RezervirajSobu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfilRezervacijeResponseDTO {
    private Integer id;
    private LocalDate datumRezerviranja;
    private boolean placeno;
    private BigDecimal iznosRezervacije;
    private String korisnikEmail;
    private List<RezervirajSadrzaj> sadrzaji;
    private List<RezervirajSobu> sobe;
}

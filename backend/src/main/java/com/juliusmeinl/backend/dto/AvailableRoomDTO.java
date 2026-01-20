package com.juliusmeinl.backend.dto;

import java.math.BigDecimal;

import com.juliusmeinl.backend.model.VrstaSobe;

public class AvailableRoomDTO {
    private Integer id;
    private String brojSobe;
    private VrstaSobe vrsta;
    private BigDecimal cijena;
    
    public AvailableRoomDTO(Integer id, String brojSobe, VrstaSobe vrsta, BigDecimal cijena) {
        this.id = id;
        this.brojSobe = brojSobe;
        this.vrsta = vrsta;
        this.cijena = cijena;
    }
    
    // getters and setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getBrojSobe() {
        return brojSobe;
    }
    
    public void setBrojSobe(String brojSobe) {
        this.brojSobe = brojSobe;
    }

    public VrstaSobe getVrsta() {
        return vrsta;
    }

    public void setVrsta(VrstaSobe vrsta) {
        this.vrsta = vrsta;
    }
    
    public BigDecimal getCijena() {
        return cijena;
    }
    
    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }
}
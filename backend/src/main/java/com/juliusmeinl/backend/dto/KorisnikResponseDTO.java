package com.juliusmeinl.backend.dto;

public class KorisnikResponseDTO {
    private String name;
    private String prezime;
    private String email;
    private String telefon;
    private String drzava;
    private String grad;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrezime() {
        return prezime;
    }
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefon() {
        return telefon;
    }
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
    public String getDrzava() {
        return drzava;
    }
    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }
    public String getGrad() {
        return grad;
    }
    public void setGrad(String grad) {
        this.grad = grad;
    }

}

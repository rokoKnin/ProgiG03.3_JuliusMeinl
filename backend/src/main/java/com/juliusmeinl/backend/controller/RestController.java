package com.juliusmeinl.backend.controller;

import com.juliusmeinl.backend.model.UlogaKorisnika;
import com.juliusmeinl.backend.model.Korisnik;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/")
    public String greet(){
        return "Dobro dosli u hotel!";
    }

}

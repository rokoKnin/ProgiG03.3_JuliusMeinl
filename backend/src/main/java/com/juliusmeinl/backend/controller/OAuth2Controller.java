package com.juliusmeinl.backend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {

    @GetMapping("/")
    public String greet(){
        return "Dobro dosli u hotel!";
    }

}

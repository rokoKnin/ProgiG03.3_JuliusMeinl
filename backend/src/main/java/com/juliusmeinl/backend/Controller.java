package com.juliusmeinl.backend;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/")
    public String greet(){
        return "Dobro dosli u hotel!";
    }

}

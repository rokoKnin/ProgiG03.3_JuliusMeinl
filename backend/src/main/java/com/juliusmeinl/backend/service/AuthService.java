package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthService {

    private KorisnikRepository korisnikRepository;

    public Optional<Korisnik> getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof OAuth2User noviKorisnik) {
            return korisnikRepository.findById(noviKorisnik.getAttribute("email"));
        }
        return Optional.empty();
    }

    public Optional<Korisnik> getLoggedInAdmin() {
        Optional<Korisnik> loggedInUser = getLoggedInUser();
        if (loggedInUser.isPresent() && loggedInUser.get().getOvlast().equals("VLASNIK")) {
            return loggedInUser;
        } else {
            return Optional.empty();
        }
    }
}

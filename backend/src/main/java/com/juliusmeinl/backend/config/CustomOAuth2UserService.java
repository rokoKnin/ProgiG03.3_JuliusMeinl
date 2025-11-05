package com.juliusmeinl.backend.config;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.service.KorisnikService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private KorisnikService korisnikService;

    public void CustomOAuth2UserService(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //Dobivanje emaila, imena i prezimena iz Google profila
        String email = oAuth2User.getAttribute("email");
        String ime = oAuth2User.getAttribute("given_name");
        String prezime = oAuth2User.getAttribute("family_name");

        //Provjeri postoji li korisnik u bazi
        Optional<Korisnik> korisnikOptional = korisnikService.findByEmail(email);

        Korisnik korisnik;
        if (korisnikOptional.isPresent()) {
            korisnik = korisnikOptional.get();
        } else {
            //Treba nesto.......................

        }
        return oAuth2User;
        }
    }


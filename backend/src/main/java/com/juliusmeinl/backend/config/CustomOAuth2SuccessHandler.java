package com.juliusmeinl.backend.config;

import com.juliusmeinl.backend.model.UlogaKorisnika;
import com.juliusmeinl.backend.repository.KorisnikRepository;
import com.juliusmeinl.backend.security.OAuth2Utils;
import com.juliusmeinl.backend.service.KorisnikService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final KorisnikRepository korisnikRepository;
    private final KorisnikService korisnikService;

    @Value("${julius.frontend.url}")
    private String frontendUrl;

    @Value("${julius.admin.email}")
    private String adminEmail;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = oauthToken.getPrincipal();

        String email = oauthUser.getAttribute("email");

        UlogaKorisnika ovlast = OAuth2Utils.odrediUlogu(email, adminEmail, korisnikRepository);

        String ime = oauthUser.getAttribute("given_name");
        String prezime  = oauthUser.getAttribute("last_name");
        String telefon = oauthUser.getAttribute("phone_number");

        //TODO: osigurati da se localstorage updatea sa svim potrebnim informacijama i onda kada korisnik vec postoji u bazi
        if (korisnikService.existsByEmail(email)) {
            response.sendRedirect(frontendUrl + "/#/?ovlast=" + ovlast + "&email=" + email);
        } else {
            String sufix = ime != null ? "&ime=" + ime : "";
            sufix += prezime != null ? "&prezime=" + prezime : "";
            sufix += telefon != null ? "&telefon=" + telefon : "";
            sufix += "&email=" + email;
            // novi korisnik → preusmjeri na dashboard
            response.sendRedirect( frontendUrl + "/#/dashboard" + "?ovlast=" + ovlast + sufix);
        }
    }
}
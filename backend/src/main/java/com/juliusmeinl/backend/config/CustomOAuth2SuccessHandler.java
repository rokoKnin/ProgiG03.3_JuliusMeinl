package com.juliusmeinl.backend.config;

import com.juliusmeinl.backend.service.KorisnikService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${julius.frontend.url}")
    private String frontendUrl;

    @Value("${julius.admin.email}")
    private String adminEmail;

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final KorisnikService korisnikService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = oauthToken.getPrincipal();

        String email = oauthUser.getAttribute("email");


        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

        String accessToken = authorizedClient.getAccessToken().getTokenValue();

//        List<String> deaktivirani = korisnikService.getDeactivated();
        List<SimpleGrantedAuthority> authorities;

        if (email.equals(adminEmail)) {
            authorities = List.of(new SimpleGrantedAuthority("ROLE_VLASNIK"),
                                  new SimpleGrantedAuthority("ROLE_RECEPCIONIST"));
//        } else if(!deaktivirani.contains(email)){
//            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_REGISTRIRAN"));
        } else {
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_NEREGISTRIRAN"));
        }

        Authentication newAuth = new UsernamePasswordAuthenticationToken(oauthUser, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);

        String ime = oauthUser.getAttribute("given_name");
        String prezime  = oauthUser.getAttribute("last_name");
        String telefon = oauthUser.getAttribute("phone_number");

        if (korisnikService.existsByEmail(email)) {
            // korisnik je u bazi → preusmjeri na /home
            response.sendRedirect(frontendUrl + "/#/?access_token=" + accessToken);
        } else {
            String sufix = ime != null ? "&ime=" + ime : "";
            sufix += prezime != null ? "&prezime=" + prezime : "";
            sufix += telefon != null ? "&telefon=" + telefon : "";
            sufix += "&email=" + email;
            // novi korisnik → preusmjeri na dashboard
            response.sendRedirect( frontendUrl + "/#/dashboard" + "?access_token=" + accessToken + sufix);
        }
    }
}
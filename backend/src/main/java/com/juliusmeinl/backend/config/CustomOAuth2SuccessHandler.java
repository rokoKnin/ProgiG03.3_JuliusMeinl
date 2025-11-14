package com.juliusmeinl.backend.config;

import com.juliusmeinl.backend.service.KorisnikService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${frontend.url}")
    private String frontendUrl;

    private final KorisnikService userService;

    public CustomOAuth2SuccessHandler(KorisnikService userService) {
        this.userService = userService;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");

        if (userService.existsByEmail(email)) {
            // korisnik je u bazi → preusmjeri na /home
            response.sendRedirect(frontendUrl);
        } else {
            // novi korisnik → preusmjeri na dashboard
            response.sendRedirect(  frontendUrl + "/#/dashboard");
        }
    }
}
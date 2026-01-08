//package com.juliusmeinl.backend.model;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
//@RequiredArgsConstructor
//public class CustomOAuth2User implements OAuth2User {
//    private final Korisnik korisnik;
//    private final Map<String, Object> attributes;
//
//    @Override
//    public <A> A getAttribute(String name) {
//        return OAuth2User.super.getAttribute(name);
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return attributes;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if (korisnik.getOvlast().equals(UlogaKorisnika.GOST)) {
//            return List.of(new SimpleGrantedAuthority(UlogaKorisnika.GOST.name()));
//        } else if (korisnik.getOvlast().equals(UlogaKorisnika.VLASNIK)) {
//            return List.of(
//                    new SimpleGrantedAuthority(UlogaKorisnika.VLASNIK.name()),
//                    new SimpleGrantedAuthority(UlogaKorisnika.GOST.name())
//            );
//        } else {
//            return List.of(new SimpleGrantedAuthority(UlogaKorisnika.GOST.name()));
//        }
//    }
//
//    @Override
//    public String getName() {
//        return korisnik.getIme();
//    }
//}

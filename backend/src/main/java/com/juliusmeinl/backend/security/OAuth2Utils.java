package com.juliusmeinl.backend.security;

import com.juliusmeinl.backend.model.Korisnik;
import com.juliusmeinl.backend.model.UlogaKorisnika;
import com.juliusmeinl.backend.repository.KorisnikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.juliusmeinl.backend.model.UlogaKorisnika.KORISNIK;
import static com.juliusmeinl.backend.model.UlogaKorisnika.VLASNIK;

@RequiredArgsConstructor
public class OAuth2Utils {

    private final KorisnikRepository korisnikRepository;

    public static GrantedAuthoritiesMapper authoritiesMapper(String adminEmail, KorisnikRepository korisnikRepository) {
        return authorities -> {
          Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

          authorities.forEach(authority -> {
                if (authority instanceof OAuth2UserAuthority) {
                    Map<String, Object> attributes = ((OAuth2UserAuthority) authority).getAttributes();
                    String email = (String) attributes.get("email");

                    UlogaKorisnika uloga = odrediUlogu(email, adminEmail, korisnikRepository);

                    grantedAuthorities.addAll(uloga.getAuthorities());
                }
          });
          return grantedAuthorities;
        };
    }

    public static UlogaKorisnika odrediUlogu(String email, String adminEmail, KorisnikRepository korisnikRepository) {
        if (email == null) throw new RuntimeException("email is null");
        if (email.equals(adminEmail)) { return VLASNIK;}

        return korisnikRepository.findByEmail(email).map(Korisnik::getOvlast).orElse(KORISNIK);
    }
}

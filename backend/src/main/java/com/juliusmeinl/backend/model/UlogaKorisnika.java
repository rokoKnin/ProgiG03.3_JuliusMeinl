package com.juliusmeinl.backend.model;

import com.juliusmeinl.backend.security.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.juliusmeinl.backend.security.Permission.*;

@RequiredArgsConstructor
public enum UlogaKorisnika {
    KORISNIK(Collections.<Permission>emptySet()),

    ZAPOSLENIK(
            Set.of(
                    RECEPTIONIST_CREATE,
                    RECEPTIONIST_READ,
                    RECEPTIONIST_UPDATE,
                    RECEPTIONIST_DELETE
            )
    ),

    VLASNIK (
            Set.of(
                    ADMIN_CREATE,
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    RECEPTIONIST_CREATE,
                    RECEPTIONIST_READ,
                    RECEPTIONIST_UPDATE,
                    RECEPTIONIST_DELETE
            )
    );

    @Getter
    private final Set<Permission> permissionSet;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities =
                new java.util.ArrayList<>(
                        getPermissionSet()
                            .stream()
                            .map(perm -> new SimpleGrantedAuthority(perm.getPermmision()))
                            .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}

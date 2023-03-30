package com.example.wohnungsuchen.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    SEARCHER("SEARCHER"),
    LODGER("LODGER");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }

}

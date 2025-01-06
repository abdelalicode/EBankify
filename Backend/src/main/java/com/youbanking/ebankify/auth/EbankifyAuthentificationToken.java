package com.youbanking.ebankify.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class EbankifyAuthentificationToken extends AbstractAuthenticationToken {
    public EbankifyAuthentificationToken() {
        super(AuthorityUtils.createAuthorityList("ROLE_Ebankify"));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return "AJIAAAA";
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new RuntimeException("Cannot set authenticated to " + authenticated);
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}

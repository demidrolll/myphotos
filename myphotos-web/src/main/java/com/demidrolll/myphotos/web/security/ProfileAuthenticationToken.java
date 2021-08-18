package com.demidrolll.myphotos.web.security;

import com.demidrolll.myphotos.model.domain.Profile;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

import static com.demidrolll.myphotos.web.security.SecurityUtils.TEMP_PASS;

public class ProfileAuthenticationToken implements RememberMeAuthenticationToken {

    private static final long serialVersionUID = -2736542893083435537L;

    private final Profile profile;

    public ProfileAuthenticationToken(Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }

    @Override
    public Object getPrincipal() {
        return profile;
    }

    @Override
    public Object getCredentials() {
        return TEMP_PASS;
    }
}

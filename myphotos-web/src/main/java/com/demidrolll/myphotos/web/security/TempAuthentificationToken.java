package com.demidrolll.myphotos.web.security;

import org.apache.shiro.authc.AuthenticationToken;

import static com.demidrolll.myphotos.web.security.SecurityUtils.TEMP_PASS;
import static com.demidrolll.myphotos.web.security.SecurityUtils.TEMP_PROFILE;

public class TempAuthentificationToken implements AuthenticationToken {

    private static final long serialVersionUID = 3875197373443175087L;

    @Override
    public Object getPrincipal() {
        return TEMP_PROFILE;
    }

    @Override
    public Object getCredentials() {
        return TEMP_PASS;
    }
}

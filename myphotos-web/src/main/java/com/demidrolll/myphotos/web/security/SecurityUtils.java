package com.demidrolll.myphotos.web.security;

import com.demidrolll.myphotos.model.domain.Profile;
import org.apache.shiro.subject.Subject;

public class SecurityUtils {

    public static final String TEMP_PASS = "";

    public static final String TEMP_PROFILE = "";

    public static final String TEMP_ROLE = "TEMP";

    public static final String PROFILE_ROLE = "PROFILE";

    public static void logout() {
        org.apache.shiro.SecurityUtils.getSubject().logout();
    }

    public static void authentificate(Profile profile) {
        org.apache.shiro.SecurityUtils.getSubject().login(new ProfileAuthenticationToken(profile));
    }

    public static void authentificate() {
        org.apache.shiro.SecurityUtils.getSubject().login(new TempAuthentificationToken());
    }

    public static boolean isAuthenticated() {
        return org.apache.shiro.SecurityUtils.getSubject().isAuthenticated();
    }

    public static boolean isTempAuthenticated() {
        Subject currentSubject = org.apache.shiro.SecurityUtils.getSubject();
        return currentSubject.isAuthenticated() && TEMP_PROFILE.equals(currentSubject.getPrincipal());
    }

    public static Profile getCurrentProfile() {
        Subject currentSubject = org.apache.shiro.SecurityUtils.getSubject();
        if (currentSubject.isAuthenticated()) {
            return (Profile) currentSubject.getPrincipal();
        } else {
            throw new IllegalStateException("Current subject is not authenticated");
        }
    }
}

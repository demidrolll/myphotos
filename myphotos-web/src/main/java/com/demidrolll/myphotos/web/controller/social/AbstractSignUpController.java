package com.demidrolll.myphotos.web.controller.social;

import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.ProfileService;
import com.demidrolll.myphotos.service.SocialService;
import com.demidrolll.myphotos.web.component.ProfileSignUpServiceProxy;
import com.demidrolll.myphotos.web.security.SecurityUtils;
import com.demidrolll.myphotos.web.util.RoutingUtils;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public abstract class AbstractSignUpController extends HttpServlet {

    @EJB
    private ProfileService profileService;

    @Inject
    protected ProfileSignUpServiceProxy profileSignUpService;

    protected SocialService socialService;

    protected abstract void setSocialService(SocialService socialService);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (SecurityUtils.isAuthenticated()) {
            RoutingUtils.redirectToValidAuthUrl(req, resp);
        } else {
            Optional<String> code = Optional.ofNullable(req.getParameter("code"));
            if (code.isPresent()) {
                processSignUp(code.get(), req, resp);
            } else {
                RoutingUtils.redirectToUri("/", req, resp);
            }
        }
    }

    private void processSignUp(String code, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Profile signUpProfile = socialService.fetchProfile(code);
        Optional<Profile> optionalProfile = profileService.findByEmail(signUpProfile.getEmail());
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            SecurityUtils.authentificate(profile);
            RoutingUtils.redirectToUri("/" + profile.getUid(), req, resp);
        } else {
            SecurityUtils.authentificate();
            profileSignUpService.createSignUpProfile(signUpProfile);
            RoutingUtils.redirectToUri("/sign-up", req, resp);
        }
    }
}

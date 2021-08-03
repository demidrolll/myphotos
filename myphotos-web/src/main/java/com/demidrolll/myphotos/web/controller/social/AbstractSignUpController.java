package com.demidrolll.myphotos.web.controller.social;

import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.ProfileService;
import com.demidrolll.myphotos.service.SocialService;
import com.demidrolll.myphotos.web.component.ProfileSignUpServiceProxy;
import com.demidrolll.myphotos.web.util.RoutingUtils;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        Optional<String> code = Optional.ofNullable(req.getParameter("code"));
        if (code.isPresent()) {
            processSignUp(code.get(), req, resp);
        } else {
            RoutingUtils.redirectToUri("/", req, resp);
        }
    }

    private void processSignUp(String code, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Profile signUpProfile = socialService.fetchProfile(code);
        Optional<Profile> optionalProfile = profileService.findByEmail(signUpProfile.getEmail());
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            RoutingUtils.redirectToUri("/" + profile.getId(), req, resp);
        } else {
            profileSignUpService.createSignUpProfile(signUpProfile);
            RoutingUtils.redirectToUri("/sign-up", req, resp);
        }
    }
}

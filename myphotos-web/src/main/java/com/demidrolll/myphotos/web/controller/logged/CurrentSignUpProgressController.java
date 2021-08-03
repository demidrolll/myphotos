package com.demidrolll.myphotos.web.controller.logged;

import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.web.component.ProfileSignUpServiceProxy;
import com.demidrolll.myphotos.web.util.RoutingUtils;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/sign-up", loadOnStartup = 1)
public class CurrentSignUpProgressController extends HttpServlet {

    @Inject
    private ProfileSignUpServiceProxy profileSignUpService;

    @Inject
    private Logger logger;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Profile profile = profileSignUpService.getCurrentProfile();
        logger.log(Level.INFO, "Current profile: {0}", profile);
        RoutingUtils.forwardToPage("home", req, resp);
    }
}

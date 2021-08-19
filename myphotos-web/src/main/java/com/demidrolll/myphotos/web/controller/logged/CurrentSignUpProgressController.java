package com.demidrolll.myphotos.web.controller.logged;

import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.web.component.ProfileSignUpServiceProxy;
import com.demidrolll.myphotos.web.form.ProfileForm;
import com.demidrolll.myphotos.web.util.RoutingUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/sign-up", loadOnStartup = 1)
public class CurrentSignUpProgressController extends HttpServlet {

    @Inject
    private ProfileSignUpServiceProxy profileSignUpService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Profile profile = profileSignUpService.getCurrentProfile();
        req.setAttribute("profile", new ProfileForm(profile));
        RoutingUtils.forwardToPage("sign-up", req, resp);
    }
}

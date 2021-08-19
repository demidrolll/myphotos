package com.demidrolll.myphotos.web.controller.logged;

import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.web.form.ProfileForm;
import com.demidrolll.myphotos.web.security.SecurityUtils;
import com.demidrolll.myphotos.web.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/edit", loadOnStartup = 1)
public class EditProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Profile profile = SecurityUtils.getCurrentProfile();
        req.setAttribute("profile", new ProfileForm(profile));
        RoutingUtils.forwardToPage("edit", req, resp);
    }
}

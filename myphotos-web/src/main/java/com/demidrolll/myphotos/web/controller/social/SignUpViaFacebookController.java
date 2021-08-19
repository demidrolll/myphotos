package com.demidrolll.myphotos.web.controller.social;

import com.demidrolll.myphotos.common.annotation.qualifier.Facebook;
import com.demidrolll.myphotos.service.SocialService;
import com.demidrolll.myphotos.web.security.SecurityUtils;
import com.demidrolll.myphotos.web.util.RoutingUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/sign-up/facebook", loadOnStartup = 1)
public class SignUpViaFacebookController extends HttpServlet {

    @Inject
    @Facebook
    private SocialService socialService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (SecurityUtils.isAuthenticated()) {
            RoutingUtils.redirectToValidAuthUrl(req, resp);
        } else {
            RoutingUtils.redirectToUri(socialService.getAuthorizeUrl(), req, resp);
        }
    }
}

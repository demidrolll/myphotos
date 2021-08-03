package com.demidrolll.myphotos.web.controller.social;

import com.demidrolll.myphotos.common.annotation.qualifier.Facebook;
import com.demidrolll.myphotos.service.SocialService;
import com.demidrolll.myphotos.web.util.RoutingUtils;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/sign-up/facebook", loadOnStartup = 1)
public class SignUpViaFacebookController extends HttpServlet {

    @Inject
    @Facebook
    private SocialService socialService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoutingUtils.redirectToUri(socialService.getAuthorizeUrl(), req, resp);
    }
}

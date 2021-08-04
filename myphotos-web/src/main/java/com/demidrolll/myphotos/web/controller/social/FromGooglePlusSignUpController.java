package com.demidrolll.myphotos.web.controller.social;

import com.demidrolll.myphotos.common.annotation.qualifier.GooglePlus;
import com.demidrolll.myphotos.service.SocialService;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/from/google-plus", loadOnStartup = 1)
public class FromGooglePlusSignUpController extends AbstractSignUpController {

    @Inject
    @Override
    protected void setSocialService(@GooglePlus SocialService socialService) {
        this.socialService = socialService;
    }
}

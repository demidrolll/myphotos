package com.demidrolll.myphotos.web.controller.social;

import com.demidrolll.myphotos.common.annotation.qualifier.Facebook;
import com.demidrolll.myphotos.service.SocialService;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/from/facebook", loadOnStartup = 1)
public class FromFacebookSignUpController extends AbstractSignUpController {

    @Inject
    @Override
    protected void setSocialService(@Facebook SocialService socialService) {
        this.socialService = socialService;
    }
}

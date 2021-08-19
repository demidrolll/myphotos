package com.demidrolll.myphotos.web.controller.logged;

import com.demidrolll.myphotos.common.annotation.group.SignUpGroup;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.web.component.ProfileSignUpServiceProxy;
import com.demidrolll.myphotos.web.security.SecurityUtils;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/sign-up/complete", loadOnStartup = 1)
public class CompleteSignUpController extends AbstractProfileSaveController {

    @Inject
    private ProfileSignUpServiceProxy profileSignUpService;

    @Override
    protected String getBackToEditView() {
        return "sign-up";
    }

    @Override
    protected void saveProfile(Profile profile) {
        profileSignUpService.completeSignUp();
        reloginWithUserRole(profile);
    }

    private void reloginWithUserRole(Profile profile) {
        SecurityUtils.logout();
        SecurityUtils.authentificate(profile);
    }

    @Override
    protected Profile getCurrentProfile() {
        return profileSignUpService.getCurrentProfile();
    }

    @Override
    protected Class<?>[] getValidationGroups() {
        return new Class<?>[] {SignUpGroup.class};
    }
}

package com.demidrolll.myphotos.web.controller.logged;

import com.demidrolll.myphotos.common.annotation.group.ProfileUpdateGroup;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.ProfileService;
import com.demidrolll.myphotos.web.security.SecurityUtils;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/save", loadOnStartup = 1)
public class SaveProfileController extends AbstractProfileSaveController {

    @EJB
    private ProfileService profileService;

    @Override
    protected String getBackToEditView() {
        return "edit";
    }

    @Override
    protected void saveProfile(Profile profile) {
        profileService.update(profile);
    }

    @Override
    protected Profile getCurrentProfile() {
        return SecurityUtils.getCurrentProfile();
    }

    @Override
    protected Class<?>[] getValidationGroups() {
        return new Class<?>[]{ProfileUpdateGroup.class};
    }
}

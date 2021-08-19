package com.demidrolll.myphotos.web.controller.logged;

import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.web.component.ConstraintViolationConverter;
import com.demidrolll.myphotos.web.component.FormReader;
import com.demidrolll.myphotos.web.form.ProfileForm;
import com.demidrolll.myphotos.web.util.RoutingUtils;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;

public abstract class AbstractProfileSaveController extends HttpServlet {

    @Resource(lookup = "java:comp/Validator")
    private Validator validator;

    @Inject
    private FormReader formReader;

    @Inject
    private ConstraintViolationConverter constraintViolationConverter;

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProfileForm profileForm = formReader.readForm(req, ProfileForm.class);
        Set<ConstraintViolation<ProfileForm>> violations = validator.validate(profileForm, getValidationGroups());
        if (violations.isEmpty()) {
            saveChanges(profileForm, req, resp);
        } else {
            backToEditPage(req, profileForm, violations, resp);
        }
    }

    private void backToEditPage(HttpServletRequest req, ProfileForm profileForm, Set<ConstraintViolation<ProfileForm>> violations, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("profile", profileForm);
        req.setAttribute("violations", constraintViolationConverter.convert(violations));
        RoutingUtils.forwardToPage(getBackToEditView(), req, resp);
    }

    protected abstract String getBackToEditView();

    private void saveChanges(ProfileForm profileForm, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Profile profile = getCurrentProfile();
        profileForm.copyToProfile(profile);
        saveProfile(profile);
        RoutingUtils.redirectToUri("/" + profile.getUid(), req, resp);
    }

    protected abstract void saveProfile(Profile profile);

    protected abstract Profile getCurrentProfile();

    protected abstract Class<?>[] getValidationGroups();
}

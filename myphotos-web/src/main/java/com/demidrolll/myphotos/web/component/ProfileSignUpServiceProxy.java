package com.demidrolll.myphotos.web.component;

import com.demidrolll.myphotos.exception.InvalidWorkFlowException;
import com.demidrolll.myphotos.exception.ObjectNotFoundException;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.ProfileSignUpService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
public class ProfileSignUpServiceProxy implements ProfileSignUpService, Serializable {

    @EJB
    private ProfileSignUpService profileSignUpService;

    @Inject
    private transient Logger logger;

    @PostConstruct
    private void init() {
        logger.log(Level.INFO, "Created {0} instance: {1}", new Object[]{getClass().getSimpleName(), System.identityHashCode(this)});
    }

    @PreDestroy
    private void destroy() {
        logger.log(Level.INFO, "Destroyed {0} instance: {1}", new Object[]{getClass().getSimpleName(), System.identityHashCode(this)});
        if (profileSignUpService != null) {
            profileSignUpService.cancel();
            profileSignUpService = null;
        }
    }

    @Override
    public void createSignUpProfile(Profile profile) {
        validate();
        profileSignUpService.createSignUpProfile(profile);
    }

    @Override
    public Profile getCurrentProfile() throws ObjectNotFoundException {
        validate();
        return profileSignUpService.getCurrentProfile();
    }

    @Override
    public void completeSignUp() {
        validate();
        profileSignUpService.completeSignUp();
        profileSignUpService = null;
    }

    @Override
    public void cancel() {
        profileSignUpService.cancel();
        profileSignUpService = null;
    }

    private void validate() {
        if (profileSignUpService == null) {
            throw new InvalidWorkFlowException("Can't use profileSignUpService");
        }
    }
}

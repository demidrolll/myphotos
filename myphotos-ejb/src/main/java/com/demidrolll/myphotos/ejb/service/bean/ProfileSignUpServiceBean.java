package com.demidrolll.myphotos.ejb.service.bean;

import com.demidrolll.myphotos.ejb.model.UrlImageResource;
import com.demidrolll.myphotos.exception.ObjectNotFoundException;
import com.demidrolll.myphotos.model.AsyncOperation;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.ProfileService;
import com.demidrolll.myphotos.service.ProfileSignUpService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.demidrolll.myphotos.common.config.Constants.DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS;

@Stateful
@StatefulTimeout(value = 30, unit = TimeUnit.MINUTES)
public class ProfileSignUpServiceBean implements ProfileSignUpService, Serializable {

    @Inject
    private transient Logger logger;

    @Inject
    private transient ProfileService profileService;

    private Profile profile;

    @Override
    public void createSignUpProfile(Profile profile) {
        this.profile = profile;
        profileService.transliterateSocialProfile(profile);
    }

    @Override
    public Profile getCurrentProfile() throws ObjectNotFoundException {
        if (profile == null) {
            throw new ObjectNotFoundException("Sign up profile not found");
        }
        return profile;
    }

    @Override
    @Remove
    public void completeSignUp() {
        profileService.signUp(profile, false);
        if (profile.getAvatarUrl() != null) {
            profileService.uploadNewAvatar(profile, new UrlImageResource(profile.getAvatarUrl()), new AsyncOperation<>() {

                @Override
                public long getTimeOutInMillis() {
                    return DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS;
                }

                @Override
                public void onSuccess(Profile result) {
                    logger.log(Level.INFO, "Profile avatar successful saved to {0}", result.getAvatarUrl());
                }

                @Override
                public void onFailed(Throwable throwable) {
                    logger.log(Level.WARNING, "Profile avatar can't saved", throwable);
                }
            });
        }
    }

    @Override
    @Remove
    public void cancel() {
        profileService = null;
    }

    @PostConstruct
    private void postConstruct() {
        logger.log(Level.INFO, "Created {0} instance: {1}", new Object[]{getClass().getSimpleName(), System.identityHashCode(this)});
    }

    @PreDestroy
    private void preDestroy() {
        logger.log(Level.INFO, "Destroyed {0} instance: {1}", new Object[]{getClass().getSimpleName(), System.identityHashCode(this)});
    }

    @PostActivate
    private void postActivate() {
        logger.log(Level.INFO, "Activate {0} instance: {1}", new Object[]{getClass().getSimpleName(), System.identityHashCode(this)});
    }

    @PrePassivate
    private void prePassivate() {
        logger.log(Level.INFO, "Passivated {0} instance: {1}", new Object[]{getClass().getSimpleName(), System.identityHashCode(this)});
    }
}

package com.demidrolll.myphotos.ejb.service.bean;

import com.demidrolll.myphotos.ejb.repository.ProfileRepository;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Logger;

@Stateless
public class UpdateProfileRatingBean {

    @Inject
    private Logger logger;

    @Inject
    private ProfileRepository profileRepository;

    @Schedule(persistent = false)
    public void updateProfileRating() {
        profileRepository.updateRating();
        logger.info("Successful updated profile rating");
    }
}

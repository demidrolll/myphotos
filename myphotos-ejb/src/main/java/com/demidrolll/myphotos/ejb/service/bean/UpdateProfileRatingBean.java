package com.demidrolll.myphotos.ejb.service.bean;

import com.demidrolll.myphotos.ejb.repository.ProfileRepository;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Logger;

@Stateless
@Lock(LockType.READ)
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

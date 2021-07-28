package com.demidrolll.myphotos.ejb.service.bean;

import com.demidrolll.myphotos.ejb.repository.ProfileRepository;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

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

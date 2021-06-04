package com.demidrolll.myphotos.service;

import com.demidrolll.myphotos.exception.ObjectNotFoundException;
import com.demidrolll.myphotos.model.domain.Profile;

public interface ProfileSignUpService {

    void createSignUpProfile(Profile profile);

    Profile getCurrentProfile() throws ObjectNotFoundException;

    void completeSignUp();

    void cancel();
}

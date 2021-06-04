package com.demidrolll.myphotos.service;

import com.demidrolll.myphotos.exception.RetrieveSocialDataFailedException;
import com.demidrolll.myphotos.model.domain.Profile;

public interface SocialService {

    Profile fetchProfile(String code) throws RetrieveSocialDataFailedException;

    String getClientId();

    default String getAuthorizeUrl() {
        throw new UnsupportedOperationException();
    }
}

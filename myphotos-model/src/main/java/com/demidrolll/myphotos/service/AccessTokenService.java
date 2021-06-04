package com.demidrolll.myphotos.service;

import com.demidrolll.myphotos.exception.AccessForbiddenException;
import com.demidrolll.myphotos.exception.InvalidAccessTokenException;
import com.demidrolll.myphotos.model.domain.AccessToken;
import com.demidrolll.myphotos.model.domain.Profile;

public interface AccessTokenService {

    AccessToken generateAccessToken(Profile profile);

    Profile findProfile(String token, Long id) throws AccessForbiddenException, InvalidAccessTokenException;

    void invalidateAccessToken(String token) throws InvalidAccessTokenException;
}

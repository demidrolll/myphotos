package com.demidrolll.myphotos.ejb.service.bean;

import com.demidrolll.myphotos.ejb.repository.AccessTokenRepository;
import com.demidrolll.myphotos.exception.AccessForbiddenException;
import com.demidrolll.myphotos.exception.InvalidAccessTokenException;
import com.demidrolll.myphotos.model.domain.AccessToken;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.AccessTokenService;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@Local(AccessTokenService.class)
public class AccessTokenServiceBean implements AccessTokenService {

    @Inject
    private Logger logger;

    @Inject
    private AccessTokenRepository accessTokenRepository;

    @Override
    public AccessToken generateAccessToken(Profile profile) {
        AccessToken accessToken = new AccessToken();
        accessToken.setProfile(profile);
        accessTokenRepository.create(accessToken);
        return accessToken;
    }

    @Override
    public Profile findProfile(String token, Long profileId) throws AccessForbiddenException, InvalidAccessTokenException {
        Profile profile = accessTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new InvalidAccessTokenException(String.format("Access token %s invalid", token)))
                .getProfile();
        if (profile.getId() != profileId) {
            throw new AccessForbiddenException(String.format("Access forbidden for token=%s and profileId=%s", token, profileId));
        }
        return profile;
    }

    @Override
    public void invalidateAccessToken(String token) throws InvalidAccessTokenException {
        if (!accessTokenRepository.removeAccessToken(token)) {
            logger.log(Level.WARNING, "Access token {0} not found", token);
            throw new InvalidAccessTokenException("Access token not found");
        }
    }
}

package com.demidrolll.myphotos.ejb.service.impl;

import com.demidrolll.myphotos.common.annotation.cdi.Property;
import com.demidrolll.myphotos.common.annotation.qualifier.GooglePlus;
import com.demidrolll.myphotos.exception.RetrieveSocialDataFailedException;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.SocialService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@GooglePlus
@ApplicationScoped
public class GooglePlusSocialService implements SocialService {

    @Inject
    @Property("myphotos.social.google-plus.client-id")
    private String clientId;

    private List<String> issuers;

    @Inject
    public void setIssuers(@Property("myphotos.social.google-plus.issuers") String issuers) {
        this.issuers = List.of(issuers.split(","));
    }

    @Override
    public Profile fetchProfile(String code) throws RetrieveSocialDataFailedException {
        try {
            return createProfile(fetch(code));
        } catch (Exception e) {
            throw new RetrieveSocialDataFailedException("Can't fetch user from google plus", e);
        }
    }

    private Profile createProfile(GoogleIdToken.Payload user) {
        Profile profile = new Profile();
        profile.setEmail(user.getEmail());
        profile.setFirstName((String) user.get("given_name"));
        profile.setLastName((String) user.get("family_name"));
        profile.setAvatarUrl((String) user.get("picture"));

        return profile;
    }

    private GoogleIdToken.Payload fetch(String code) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(List.of(clientId))
                .setIssuers(issuers)
                .build();

        return Optional
                .ofNullable(verifier.verify(code))
                .map(GoogleIdToken::getPayload)
                .orElseThrow(() -> new RetrieveSocialDataFailedException("Can't get account by token " + code));
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getAuthorizeUrl() {
        return SocialService.super.getAuthorizeUrl();
    }
}

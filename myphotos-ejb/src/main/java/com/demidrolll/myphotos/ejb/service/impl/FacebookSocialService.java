package com.demidrolll.myphotos.ejb.service.impl;

import com.demidrolll.myphotos.common.annotation.cdi.Property;
import com.demidrolll.myphotos.common.annotation.qualifier.Facebook;
import com.demidrolll.myphotos.exception.RetrieveSocialDataFailedException;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.SocialService;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Facebook
@ApplicationScoped
public class FacebookSocialService implements SocialService {

    @Inject
    @Property("myphotos.social.facebook.client-id")
    private String clientId;

    @Inject
    @Property("myphotos.social.facebook.client-secret")
    private String secret;

    private String redirectUrl;

    @Inject
    public void setHost(@Property("myphotos.host") String host) {
        redirectUrl = host + "/from/facebook";
    }

    @Override
    public Profile fetchProfile(String code) throws RetrieveSocialDataFailedException {
        try {
            return createProfile(fetch(code));
        } catch (Exception e) {
            throw new RetrieveSocialDataFailedException("Can't fetch facebook user");
        }
    }

    private Profile createProfile(User user) {
        Profile profile = new Profile();
        profile.setEmail(user.getEmail());
        profile.setFirstName(user.getFirstName());
        profile.setLastName(user.getLastName());
        profile.setAvatarUrl(user.getPicture().getUrl());

        return profile;
    }

    private User fetch(String code) {
        FacebookClient client = new DefaultFacebookClient(Version.VERSION_11_0);
        FacebookClient.AccessToken accessToken = client.obtainUserAccessToken(clientId, secret, redirectUrl, code);
        client = new DefaultFacebookClient(accessToken.getAccessToken(), Version.VERSION_11_0);
        return client.fetchObject("me", User.class, Parameter.with("fields", "id,email,first_name,last_name,picture"));
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getAuthorizeUrl() {
        ScopeBuilder scopeBuilder = new ScopeBuilder();
        scopeBuilder.addPermission(FacebookPermissions.EMAIL);
        FacebookClient client = new DefaultFacebookClient(Version.VERSION_11_0);
        return client.getLoginDialogUrl(clientId, redirectUrl, scopeBuilder);
    }
}

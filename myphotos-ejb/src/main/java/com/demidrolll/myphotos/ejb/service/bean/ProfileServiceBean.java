package com.demidrolll.myphotos.ejb.service.bean;

import com.demidrolll.myphotos.common.annotation.cdi.Property;
import com.demidrolll.myphotos.ejb.repository.ProfileRepository;
import com.demidrolll.myphotos.exception.ObjectNotFoundException;
import com.demidrolll.myphotos.model.AsyncOperation;
import com.demidrolll.myphotos.model.ImageResource;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.ProfileService;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Local;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import java.util.Optional;

@Stateless
@LocalBean
@Local(ProfileService.class)
public class ProfileServiceBean implements ProfileService {

    @Inject
    @Property("myphotos.profile.avatar.placeholder.url")
    private String avatarPlaceHolderUrl;

    @Inject
    private ProfileRepository profileRepository;

    @Override
    public Profile findById(Long id) throws ObjectNotFoundException {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Profile not found by id: %s", id)));
    }

    @Override
    public Profile findByUid(String uid) throws ObjectNotFoundException {
        return profileRepository.findByUid(uid)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Profile not found by uid: %s", uid)));
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    @Override
    public void signUp(Profile profile, boolean b) {
    }

    @Override
    public void transliterateSocialProfile(Profile profile) {
    }

    @Override
    public void update(Profile profile) {
        profileRepository.update(profile);
    }

    @Override
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void uploadNewAvatar(Profile profile, ImageResource imageResource, AsyncOperation<Profile> asyncOperation) {
        try {
            uploadNewAvatar(profile, imageResource);
            asyncOperation.onSuccess(profile);
        } catch (Exception e) {
            setAvatarPlaceHolder(profile);
            asyncOperation.onFailed(e);
        }
    }

    public void uploadNewAvatar(Profile profile, ImageResource imageResource) {
    }

    public void setAvatarPlaceHolder(Long profileId) {
        setAvatarPlaceHolder(findById(profileId));
    }

    public void setAvatarPlaceHolder(Profile profile) {
        if (profile.getAvatarUrl() == null) {
            profile.setAvatarUrl(avatarPlaceHolderUrl);
            profileRepository.update(profile);
        }
    }
}
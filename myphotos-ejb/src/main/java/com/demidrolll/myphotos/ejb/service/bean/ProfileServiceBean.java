package com.demidrolll.myphotos.ejb.service.bean;

import com.demidrolll.myphotos.common.annotation.cdi.Property;
import com.demidrolll.myphotos.common.config.ImageCategory;
import com.demidrolll.myphotos.ejb.model.UrlImageResource;
import com.demidrolll.myphotos.ejb.repository.ProfileRepository;
import com.demidrolll.myphotos.ejb.service.ImageStorageService;
import com.demidrolll.myphotos.ejb.service.TranslitConverter;
import com.demidrolll.myphotos.ejb.service.impl.ProfileUidServiceManager;
import com.demidrolll.myphotos.ejb.service.interceptor.AsyncOperationInterceptor;
import com.demidrolll.myphotos.exception.ObjectNotFoundException;
import com.demidrolll.myphotos.model.AsyncOperation;
import com.demidrolll.myphotos.model.ImageResource;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.ProfileService;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@Local(ProfileService.class)
@Lock(LockType.READ)
public class ProfileServiceBean implements ProfileService {

    @Inject
    @Property("myphotos.profile.avatar.placeholder.url")
    private String avatarPlaceHolderUrl;

    @Inject
    private ProfileRepository profileRepository;

    @EJB
    private ImageProcessorBean imageProcessorBean;

    @Inject
    private ImageStorageService imageStorageService;

    @Inject
    private ProfileUidServiceManager profileUidServiceManager;

    @Inject
    private TranslitConverter translitConverter;

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
    public void signUp(Profile profile, boolean uploadProfileAvatar) {
        if (profile.getUid() == null) {
            setProfileUid(profile);
        }
        profileRepository.create(profile);
        if (uploadProfileAvatar && profile.getAvatarUrl() != null) {
            uploadNewAvatar(profile, new UrlImageResource(profile.getAvatarUrl()));
        }
    }

    private void setProfileUid(Profile profile) {
        List<String> uids = profileUidServiceManager.getProfileUidCandidates(profile.getFirstName(), profile.getLastName());
        List<String> existUids = profileRepository.findUids(uids);
        for (String uid : uids) {
            if (!existUids.contains(uid)) {
                profile.setUid(uid);
                return;
            }
        }

        profile.setUid(profileUidServiceManager.getDefaultUid());
    }

    @Override
    public void transliterateSocialProfile(Profile profile) {
        Optional.ofNullable(profile.getFirstName()).map(translitConverter::translit).ifPresent(profile::setFirstName);
        Optional.ofNullable(profile.getLastName()).map(translitConverter::translit).ifPresent(profile::setLastName);
        Optional.ofNullable(profile.getJobTitle()).map(translitConverter::translit).ifPresent(profile::setJobTitle);
        Optional.ofNullable(profile.getLocation()).map(translitConverter::translit).ifPresent(profile::setLocation);
    }

    @Override
    public void update(Profile profile) {
        profileRepository.update(profile);
    }

    @Override
    @Asynchronous
    @Interceptors(AsyncOperationInterceptor.class)
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
        String avatarUrl = imageProcessorBean.processProfileAvatar(imageResource);
        if (ImageCategory.isImageCategoryUrl(profile.getAvatarUrl())) {
            imageStorageService.deletePublicImage(profile.getAvatarUrl());
        }
        profile.setAvatarUrl(avatarUrl);
        profileRepository.update(profile);
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

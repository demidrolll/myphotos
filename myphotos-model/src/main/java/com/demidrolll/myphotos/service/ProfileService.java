package com.demidrolll.myphotos.service;

import com.demidrolll.myphotos.exception.ObjectNotFoundException;
import com.demidrolll.myphotos.model.AsyncOperation;
import com.demidrolll.myphotos.model.ImageResource;
import com.demidrolll.myphotos.model.domain.Profile;

import java.util.Optional;

public interface ProfileService {

    Profile findById(Long id) throws ObjectNotFoundException;

    Profile findByUid(String id) throws ObjectNotFoundException;

    Optional<Profile> findByEmail(String email);

    void signUp(Profile profile, boolean uploadProfileAvatar);

    void transliterateSocialProfile(Profile profile);

    void update(Profile profile);

    void uploadNewAvatar(Profile profile, ImageResource imageResource, AsyncOperation<Profile> asyncOperation);
}

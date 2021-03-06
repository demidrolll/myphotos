package com.demidrolll.myphotos.ejb.service.bean;

import com.demidrolll.myphotos.ejb.repository.PhotoRepository;
import com.demidrolll.myphotos.ejb.repository.ProfileRepository;
import com.demidrolll.myphotos.ejb.service.ImageStorageService;
import com.demidrolll.myphotos.ejb.service.interceptor.AsyncOperationInterceptor;
import com.demidrolll.myphotos.exception.ObjectNotFoundException;
import com.demidrolll.myphotos.exception.ValidationException;
import com.demidrolll.myphotos.model.AsyncOperation;
import com.demidrolll.myphotos.model.ImageResource;
import com.demidrolll.myphotos.model.OriginalImage;
import com.demidrolll.myphotos.model.Pageable;
import com.demidrolll.myphotos.model.SortMode;
import com.demidrolll.myphotos.model.domain.Photo;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.PhotoService;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateless
@LocalBean
@Local(PhotoService.class)
@Lock(LockType.READ)
public class PhotoServiceBean implements PhotoService {

    @Inject
    private PhotoRepository photoRepository;

    @Inject
    private ProfileRepository profileRepository;

    @Resource
    private SessionContext sessionContext;

    @EJB
    private ImageProcessorBean imageProcessorBean;

    @Inject
    private ImageStorageService imageStorageService;

    @Override
    public List<Photo> findProfilePhotos(Long profileId, Pageable pageable) {
        return photoRepository.findProfilePhotosLatestFirst(profileId, pageable.getOffset(), pageable.getLimit());
    }

    @Override
    public List<Photo> findPopularPhotos(SortMode sortMode, Pageable pageable) {
        switch (sortMode) {
            case POPULAR_AUTHOR:
                return photoRepository.findAllOrderByAuthorRatingDesc(pageable.getOffset(), pageable.getLimit());
            case POPULAR_PHOTO:
                return photoRepository.findAllOrderByViewsDesc(pageable.getOffset(), pageable.getLimit());
            default:
                throw new ValidationException(String.format("Unsupported sort mode: %s", sortMode));
        }
    }

    @Override
    public long countAllPhotos() {
        return photoRepository.countAll();
    }

    @Override
    public String viewLargePhoto(Long photoId) throws ObjectNotFoundException {
        Photo photo = getPhoto(photoId);
        photo.setViews(photo.getViews() + 1);
        photoRepository.update(photo);
        return photo.getLargeUrl();
    }

    public Photo getPhoto(Long photoId) throws ObjectNotFoundException {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Photo not found by id: %s", photoId)));
    }

    @Override
    public OriginalImage downloadOriginalImage(Long photoId) throws ObjectNotFoundException {
        Photo photo = getPhoto(photoId);
        photo.setDownloads(photo.getDownloads() + 1);
        photoRepository.update(photo);

        return imageStorageService.getOriginalImage(photo.getOriginalUrl());
    }

    @Override
    @Asynchronous
    @Interceptors(AsyncOperationInterceptor.class)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void uploadNewPhoto(Profile profile, ImageResource imageResource, AsyncOperation<Photo> asyncOperation) {
        try {
            Photo photo = uploadNewPhoto(profile, imageResource);
            asyncOperation.onSuccess(photo);
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            asyncOperation.onFailed(e);
        }
    }

    public Photo uploadNewPhoto(Profile profile, ImageResource imageResource) {
        Photo photo = imageProcessorBean.processPhoto(imageResource);
        photo.setProfile(profile);
        photoRepository.create(photo);
        photoRepository.flush();
        profile.setPhotoCount(photoRepository.countProfilePhotos(profile.getId()));
        profileRepository.update(profile);

        return photo;
    }
}

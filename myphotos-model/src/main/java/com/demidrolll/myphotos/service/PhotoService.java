package com.demidrolll.myphotos.service;

import com.demidrolll.myphotos.exception.ObjectNotFoundException;
import com.demidrolll.myphotos.model.AsyncOperation;
import com.demidrolll.myphotos.model.ImageResource;
import com.demidrolll.myphotos.model.OriginalImage;
import com.demidrolll.myphotos.model.Pageable;
import com.demidrolll.myphotos.model.SortMode;
import com.demidrolll.myphotos.model.domain.Photo;
import com.demidrolll.myphotos.model.domain.Profile;

import java.util.List;

public interface PhotoService {

    List<Photo> findProfilePhotos(Long profileId, Pageable pageable);

    List<Photo> findPopularPhotos(SortMode sortMode, Pageable pageable);

    long countAllPhotos();

    String viewLargePhoto(Long photoId) throws ObjectNotFoundException;

    OriginalImage downloadOriginalImage(Long photoId) throws ObjectNotFoundException;

    void uploadNewPhoto(Profile profile, ImageResource imageResource, AsyncOperation<Photo> asyncOperation);
}

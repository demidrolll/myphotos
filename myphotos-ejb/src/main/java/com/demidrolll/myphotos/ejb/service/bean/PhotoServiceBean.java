package com.demidrolll.myphotos.ejb.service.bean;

import com.demidrolll.myphotos.exception.ObjectNotFoundException;
import com.demidrolll.myphotos.model.AsyncOperation;
import com.demidrolll.myphotos.model.ImageResource;
import com.demidrolll.myphotos.model.OriginalImage;
import com.demidrolll.myphotos.model.Pageable;
import com.demidrolll.myphotos.model.SortMode;
import com.demidrolll.myphotos.model.domain.Photo;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.PhotoService;

import java.util.List;

public class PhotoServiceBean implements PhotoService {

    @Override
    public List<Photo> findProfilePhotos(Long aLong, Pageable pageable) {
        return null;
    }

    @Override
    public List<Photo> findPopularPhotos(SortMode sortMode, Pageable pageable) {
        return null;
    }

    @Override
    public long countAllPhotos() {
        return 0;
    }

    @Override
    public String viewLargePhoto(Long aLong) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public OriginalImage downloadOriginalImage(Long aLong) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public void uploadNewPhoto(Profile profile, ImageResource imageResource, AsyncOperation<Photo> asyncOperation) {

    }
}

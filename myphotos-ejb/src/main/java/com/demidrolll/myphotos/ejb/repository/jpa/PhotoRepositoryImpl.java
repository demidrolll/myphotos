package com.demidrolll.myphotos.ejb.repository.jpa;

import com.demidrolll.myphotos.ejb.repository.PhotoRepository;
import com.demidrolll.myphotos.model.domain.Photo;
import jakarta.enterprise.context.Dependent;

import java.util.List;

@Dependent
public class PhotoRepositoryImpl extends AbstractJpaRepository<Photo, Long> implements PhotoRepository {

    @Override
    public List<Photo> findProfilePhotosLatestFirst(Long profileId, int offset, int limit) {
        return null;
    }

    @Override
    public int countProfilePhotos(Long profileId) {
        return 0;
    }

    @Override
    public List<Photo> findAllOrderByViewsDesc(int offset, int limit) {
        return null;
    }

    @Override
    public List<Photo> findAllOrderByAuthorRatingDesc(int offset, int limit) {
        return null;
    }

    @Override
    public long countAll() {
        return 0;
    }

    @Override
    protected Class<Photo> getEntityClass() {
        return Photo.class;
    }
}

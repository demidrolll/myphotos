package com.demidrolll.myphotos.ejb.repository.jpa;

import com.demidrolll.myphotos.ejb.repository.PhotoRepository;
import com.demidrolll.myphotos.ejb.repository.jpa.StaticJpaQueryInitializer.JpaQuery;
import com.demidrolll.myphotos.model.domain.Photo;
import jakarta.enterprise.context.Dependent;

import java.util.List;
import java.util.Optional;

@Dependent
public class PhotoRepositoryImpl extends AbstractJpaRepository<Photo, Long> implements PhotoRepository {

    @Override
    @JpaQuery("SELECT ph FROM Photo ph WHERE ph.profile.id=:profileId ORDER BY ph.id DESC")
    public List<Photo> findProfilePhotosLatestFirst(Long profileId, int offset, int limit) {
        return em
                .createNamedQuery("Photo.findProfilePhotosLatestFirst", Photo.class)
                .setParameter("profileId", profileId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    @JpaQuery("SELECT COUNT(ph) FROM Photo ph WHERE ph.profile.id=:profileId")
    public int countProfilePhotos(Long profileId) {
        return em
                .createNamedQuery("Photo.countProfilePhotos", Integer.class)
                .setParameter("profileId", profileId)
                .getSingleResult();
    }

    @Override
    @JpaQuery("SELECT e FROM Photo e JOIN FETCH e.profile ORDER BY e.views DESC")
    public List<Photo> findAllOrderByViewsDesc(int offset, int limit) {
        return em
                .createNamedQuery("Photo.findAllOrderByViewsDesc", Photo.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    @JpaQuery("SELECT e FROM Photo e JOIN FETCH e.profile p ORDER BY p.rating DESC, e.views DESC")
    public List<Photo> findAllOrderByAuthorRatingDesc(int offset, int limit) {
        return em
                .createNamedQuery("Photo.findAllOrderByAuthorRatingDesc", Photo.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    @JpaQuery("SELECT COUNT(ph) FROM Photo ph")
    public long countAll() {
        return em
                .createNamedQuery("Photo.countAll", Long.class)
                .getSingleResult();
    }

    @Override
    protected Class<Photo> getEntityClass() {
        return Photo.class;
    }
}

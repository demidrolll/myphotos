package com.demidrolll.myphotos.ejb.repository.jpa;

import com.demidrolll.myphotos.ejb.repository.ProfileRepository;
import com.demidrolll.myphotos.ejb.repository.jpa.StaticJpaQueryInitializer.JpaQuery;
import com.demidrolll.myphotos.model.domain.Profile;
import jakarta.enterprise.context.Dependent;

import java.util.List;
import java.util.Optional;

@Dependent
public class ProfileRepositoryImpl extends AbstractJpaRepository<Profile, Long> implements ProfileRepository {

    @Override
    @JpaQuery("SELECT p FROM Profile p WHERE p.uid = :uid")
    public Optional<Profile> findByUid(String uid) {
        List<Profile> profile = em
                .createNamedQuery("Profile.findByUid", Profile.class)
                .setParameter("uid", uid)
                .getResultList();

        return profile.isEmpty() ? Optional.empty() : Optional.of(profile.get(0));
    }

    @Override
    @JpaQuery("SELECT p FROM Profile p WHERE p.email = :email")
    public Optional<Profile> findByEmail(String email) {
        List<Profile> profile = em
                .createNamedQuery("Profile.findByEmail", Profile.class)
                .setParameter("email", email)
                .getResultList();

        return profile.isEmpty() ? Optional.empty() : Optional.of(profile.get(0));
    }

    @Override
    public void updateRating() {
        em
                .createStoredProcedureQuery("public.update_rating")
                .execute();
    }

    @Override
    @JpaQuery("SELECT p.uid FROM Profile p WHERE p.uid = :uids")
    public List<String> findUids(List<String> uids) {
        return em
                .createNamedQuery("Profile.findUids", String.class)
                .setParameter("uids", uids)
                .getResultList();
    }

    @Override
    protected Class<Profile> getEntityClass() {
        return Profile.class;
    }
}

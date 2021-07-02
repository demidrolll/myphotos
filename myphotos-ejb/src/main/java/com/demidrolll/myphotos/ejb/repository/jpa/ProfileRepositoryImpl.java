package com.demidrolll.myphotos.ejb.repository.jpa;

import com.demidrolll.myphotos.ejb.repository.ProfileRepository;
import com.demidrolll.myphotos.model.domain.Profile;
import jakarta.enterprise.context.Dependent;

import java.util.List;
import java.util.Optional;

@Dependent
public class ProfileRepositoryImpl extends AbstractJpaRepository<Profile, Long> implements ProfileRepository {

    @Override
    public Optional<Profile> findByUid(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void updateRating() {

    }

    @Override
    public List<String> findUids(List<String> uids) {
        return null;
    }

    @Override
    protected Class<Profile> getEntityClass() {
        return Profile.class;
    }
}

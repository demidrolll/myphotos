package com.demidrolll.myphotos.ejb.repository;

import com.demidrolll.myphotos.model.domain.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends EntityRepository<Profile, Long> {

    Optional<Profile> findByUid(String id);

    Optional<Profile> findByEmail(String email);

    void updateRating();

    List<String> findUids(List<String> uids);
}

package com.demidrolll.myphotos.ejb.repository;

import com.demidrolll.myphotos.model.domain.AccessToken;

import java.util.Optional;

public interface AccessTokenRepository extends EntityRepository<AccessToken, String> {

    Optional<AccessToken> findByToken(String token);

    boolean removeAccessToken(String token);
}

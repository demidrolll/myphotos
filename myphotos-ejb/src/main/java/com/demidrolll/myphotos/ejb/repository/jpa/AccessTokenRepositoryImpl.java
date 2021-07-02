package com.demidrolll.myphotos.ejb.repository.jpa;

import com.demidrolll.myphotos.ejb.repository.AccessTokenRepository;
import com.demidrolll.myphotos.model.domain.AccessToken;
import jakarta.enterprise.context.Dependent;

import java.util.Optional;

@Dependent
public class AccessTokenRepositoryImpl extends AbstractJpaRepository<AccessToken, String> implements AccessTokenRepository {

    @Override
    public Optional<AccessToken> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    public boolean removeAccessToken(String token) {
        return false;
    }

    @Override
    protected Class<AccessToken> getEntityClass() {
        return AccessToken.class;
    }
}

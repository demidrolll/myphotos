package com.demidrolll.myphotos.ejb.repository.jpa;

import com.demidrolll.myphotos.ejb.repository.AccessTokenRepository;
import com.demidrolll.myphotos.ejb.repository.jpa.StaticJpaQueryInitializer.JpaQuery;
import com.demidrolll.myphotos.model.domain.AccessToken;
import com.demidrolll.myphotos.model.domain.Profile;
import jakarta.enterprise.context.Dependent;

import java.util.List;
import java.util.Optional;

@Dependent
public class AccessTokenRepositoryImpl extends AbstractJpaRepository<AccessToken, String> implements AccessTokenRepository {

    @Override
    @JpaQuery("SELECT at FROM AccessToken at JOIN FETCH at.profile WHERE at.token=:token")
    public Optional<AccessToken> findByToken(String token) {
        List<AccessToken> profile = em
                .createNamedQuery("AccessToken.findByToken", AccessToken.class)
                .setParameter("token", token)
                .getResultList();

        return profile.isEmpty() ? Optional.empty() : Optional.of(profile.get(0));
    }

    @Override
    @JpaQuery("DELETE FROM AccessToken at WHERE at.token=:token")
    public boolean removeAccessToken(String token) {
        int result = em
                .createNamedQuery("AccessToken.removeAccessToken")
                .setParameter("token", token)
                .executeUpdate();

        return result == 1;
    }

    @Override
    protected Class<AccessToken> getEntityClass() {
        return AccessToken.class;
    }
}

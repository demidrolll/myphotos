package com.demidrolll.myphotos.ejb.jpa.listener;

import com.demidrolll.myphotos.exception.InvalidWorkFlowException;
import com.demidrolll.myphotos.model.domain.AccessToken;

import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccessTokenLifeCycleManager {

    @Inject
    private Logger logger;

    @PrePersist
    public void setToken(AccessToken token) {
        token.setToken(UUID.randomUUID().toString().replace("-", ""));
        logger.log(Level.FINE, "Generate new uid token {0} for entity {1}", new Object[]{token.getToken(), token.getClass()});
    }

    @PreUpdate
    public void rejectUpdate(AccessToken token) {
        throw new InvalidWorkFlowException("Access token is not updatable");
    }
}

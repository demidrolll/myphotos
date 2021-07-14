package com.demidrolll.myphotos.ejb.repository.mock;

import com.demidrolll.myphotos.ejb.repository.AccessTokenRepository;
import com.demidrolll.myphotos.ejb.repository.PhotoRepository;
import com.demidrolll.myphotos.ejb.repository.ProfileRepository;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.lang.reflect.Proxy;

@Dependent
public class InMemoryRepositoryFactory {

    @Inject
    private ProfileRepositoryInvocationHandler profileRepositoryInvocationHandler;

    @Inject
    private PhotoRepositoryInvocationHandler photoRepositoryInvocationHandler;

    @Inject
    private AccessTokenRepositoryInvocationHandler accessTokenRepositoryInvocationHandler;

    @Produces
    public ProfileRepository getProfileRepository() {
        return (ProfileRepository) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {ProfileRepository.class}, profileRepositoryInvocationHandler);
    }

    @Produces
    public PhotoRepository getPhotoRepository() {
        return (PhotoRepository) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {PhotoRepository.class}, photoRepositoryInvocationHandler);
    }

    @Produces
    public AccessTokenRepository getAccessTokenRepository() {
        return (AccessTokenRepository) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {AccessTokenRepository.class}, accessTokenRepositoryInvocationHandler);
    }
}

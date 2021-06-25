package com.demidrolll.myphotos.ejb.repository.mock;

import com.demidrolll.myphotos.ejb.repository.PhotoRepository;
import com.demidrolll.myphotos.ejb.repository.ProfileRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import java.lang.reflect.Proxy;

@Dependent
public class InMemoryRepositoryFactory {

    @Inject
    private ProfileRepositoryInvocationHandler profileRepositoryInvocationHandler;

    @Inject
    private PhotoRepositoryInvocationHandler photoRepositoryInvocationHandler;

    @Produces
    public ProfileRepository getProfileRepository() {
        return (ProfileRepository) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {ProfileRepository.class}, profileRepositoryInvocationHandler);
    }

    @Produces
    public PhotoRepository getPhotoRepository() {
        return (PhotoRepository) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {PhotoRepository.class}, photoRepositoryInvocationHandler);
    }
}

package com.demidrolll.myphotos.ejb.repository.mock;

import jakarta.enterprise.context.ApplicationScoped;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@ApplicationScoped
public class AccessTokenRepositoryInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        throw new UnsupportedOperationException("Not supported yed.");
    }
}

package com.demidrolll.myphotos.ejb.service.interceptor;

import com.demidrolll.myphotos.model.ImageResource;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
public class ImageResourceInterceptor {

    @AroundInvoke
    public Object aroundProcessImageResource(InvocationContext ic) throws Exception {
        try (ImageResource imageResource = (ImageResource) ic.getParameters()[0]) {
            return ic.proceed();
        }
    }
}

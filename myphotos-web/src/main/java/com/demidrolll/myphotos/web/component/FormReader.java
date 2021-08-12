package com.demidrolll.myphotos.web.component;

import com.demidrolll.myphotos.exception.ApplicationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

@ApplicationScoped
public class FormReader {

    public <T> T readForm(HttpServletRequest request, Class<T> formClass) {
        try {
            T form = formClass.getConstructor().newInstance();
            BeanUtils.populate(form, request.getParameterMap());
            return form;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new ApplicationException(e);
        }
    }
}

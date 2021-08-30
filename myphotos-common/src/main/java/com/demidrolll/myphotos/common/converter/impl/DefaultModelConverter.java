package com.demidrolll.myphotos.common.converter.impl;

import com.demidrolll.myphotos.common.annotation.converter.ConvertAsUrl;
import com.demidrolll.myphotos.common.converter.ModelConverter;
import com.demidrolll.myphotos.common.converter.UrlConverter;
import com.demidrolll.myphotos.exception.ConfigException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

@ApplicationScoped
public class DefaultModelConverter implements ModelConverter {

    @Inject
    private UrlConverter urlConverter;

    @Override
    public <S, D> D convert(S source, Class<D> destinationClass) {
        try {
            D result = destinationClass.getConstructor().newInstance();
            copyProperties(result, Objects.requireNonNull(source, "Source can't be null"));
            return result;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ConfigException(String.format("Can't convert object from %s to %s: %s", source.getClass(), destinationClass, e.getMessage()));
        }
    }

    private <D, S> void copyProperties(D result, S source) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        PropertyUtilsBean propertyUtils = BeanUtilsBean.getInstance().getPropertyUtils();
        PropertyDescriptor[] propertyDescriptors = propertyUtils.getPropertyDescriptors(result);
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String name = descriptor.getName();
            if (!"class".equals(name) && propertyUtils.isReadable(source, name) && propertyUtils.isWriteable(result, name)) {
                Object value = propertyUtils.getProperty(source, name);
                if (value != null) {
                    Object convertedValue = convertValue(propertyUtils, result, name, value);
                    propertyUtils.setProperty(result, name, value);
                }
            }
        }
    }

    private <D> Object convertValue(PropertyUtilsBean propertyUtils, D result, String name, Object value) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class<?> destinationClass = propertyUtils.getPropertyType(result, name);
        if (destinationClass.isPrimitive() || value.getClass().isPrimitive()) {
            return value;
        } else if (isConvertAsUrlPresent(result, name)) {
            return urlConverter.convert(String.valueOf(value));
        } else if (value.getClass() != destinationClass) {
            return convert(value, destinationClass);
        }
        return value;
    }

    private <D> boolean isConvertAsUrlPresent(D result, String name) {
        Field field = null;
        Class<?> clazz = result.getClass();
        while (clazz != null) {
            try {
                field = clazz.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
            }
            clazz = clazz.getSuperclass();
        }
        return field != null && field.isAnnotationPresent(ConvertAsUrl.class);
    }

    @Override
    public <S, D> List<D> convertList(List<S> source, Class<D> destinationClass) {
        return source.stream().map(item -> convert(item, destinationClass)).collect(Collectors.toList());
    }
}

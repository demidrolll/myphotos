package com.demidrolll.myphotos.ejb.service.impl;

import com.demidrolll.myphotos.common.annotation.cdi.PropertiesSource;
import com.demidrolll.myphotos.ejb.service.TranslitConverter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Properties;
import java.util.stream.Collectors;

@ApplicationScoped
public class PropertiesTranslitConverter implements TranslitConverter {

    private Properties properties;

    @Inject
    private void setProperties(@PropertiesSource(value = "classpath:translit.properties") Properties properties) {
        properties.forEach((key, value) -> {
            properties.setProperty(((String) key).toUpperCase(), ((String) value).toUpperCase());
        });
        this.properties = properties;
    }

    @Override
    public String translit(String text) {
        return text.chars()
                .mapToObj(Character::toString)
                .map(String::toLowerCase)
                .map(ch -> properties.getProperty(ch, ch))
                .collect(Collectors.joining());
    }
}

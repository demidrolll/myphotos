package com.demidrolll.myphotos.ejb.service.impl;

import com.demidrolll.myphotos.common.annotation.cdi.PropertiesSource;
import com.demidrolll.myphotos.ejb.service.TranslitConverter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Properties;
import java.util.stream.Collectors;

@ApplicationScoped
public class PropertiesTranslitConverter implements TranslitConverter {

    @Inject
    @PropertiesSource(value = "classpath:translit.properties")
    private Properties properties;

    @Override
    public String translit(String text) {
        return text.chars()
                .mapToObj(Character::toString)
                .map(ch -> properties.getProperty(ch, ch))
                .collect(Collectors.joining());
    }
}

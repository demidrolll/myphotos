package com.demidrolll.myphotos.common.converter.impl;

import com.demidrolll.myphotos.common.annotation.cdi.Property;
import com.demidrolll.myphotos.common.converter.UrlConverter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

@ApplicationScoped
public class DefaultUrlConverter implements UrlConverter {

    @Inject
    @Property("myphotos.host")
    private String host;

    @Override
    public String convert(String url) {
        try {
            if (new URI(url).isAbsolute()) {
                return url;
            }
        } catch (URISyntaxException e) {
        }
        return URI.create(host + url).toString();
    }
}

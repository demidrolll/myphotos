package com.demidrolll;

import com.demidrolll.myphotos.common.annotation.cdi.PropertiesSource;
import com.demidrolll.myphotos.common.annotation.cdi.Property;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

import java.util.Properties;

@Singleton
@Startup
public class Test {

    @Inject
    @Property("myphotos.host")
    private String host;

    @Inject
    @PropertiesSource("classpath:application.properties")
    private Properties properties;

    @PostConstruct
    private void postConstruct() {
        System.out.println(host + properties);
    }
}

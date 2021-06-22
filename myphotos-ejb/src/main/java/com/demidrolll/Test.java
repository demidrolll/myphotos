package com.demidrolll;

import com.demidrolll.myphotos.common.annotation.cdi.PropertiesSource;
import com.demidrolll.myphotos.common.annotation.cdi.Property;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
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

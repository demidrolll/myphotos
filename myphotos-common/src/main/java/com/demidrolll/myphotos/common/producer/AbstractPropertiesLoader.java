package com.demidrolll.myphotos.common.producer;

import com.demidrolll.myphotos.common.resource.ResourceLoaderManager;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class AbstractPropertiesLoader {

    @Inject
    protected Logger logger;

    @Inject
    protected ResourceLoaderManager resourceLoaderManager;

    protected void loadProperties(Properties properties, String resourceName) {
        try {
            try (InputStream in = resourceLoaderManager.getResourceInputStream(resourceName)) {
                properties.load(in);
            }
            logger.log(Level.INFO, "Successful loaded properties from {0}", resourceName);
        } catch (IOException | RuntimeException e) {
            logger.log(Level.WARNING, "Can't load properties from: " + resourceName, e);
        }
    }
}

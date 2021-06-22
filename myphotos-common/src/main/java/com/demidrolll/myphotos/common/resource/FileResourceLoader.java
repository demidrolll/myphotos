package com.demidrolll.myphotos.common.resource;

import javax.enterprise.context.ApplicationScoped;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Dzmitry Iuzhanka (dmitry.ivjenko@softclub.by)
 */
@ApplicationScoped
public class FileResourceLoader implements ResourceLoader {

    @Override
    public boolean isSupport(String resourceName) {
        try {
            return Files.exists(Path.of(resourceName));
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public InputStream getInputStream(String resourceName) throws IOException {
        return new FileInputStream(resourceName);
    }
}

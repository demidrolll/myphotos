package com.demidrolll.myphotos.ejb.model;

import com.demidrolll.myphotos.common.model.AbstractMimeTypeImageResource;
import com.demidrolll.myphotos.exception.ApplicationException;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class UrlImageResource extends AbstractMimeTypeImageResource {

    private final String url;
    private final URLConnection urlConnection;

    public UrlImageResource(String url) {
        this.url = url;
        try {
            this.urlConnection = new URL(url).openConnection();
        } catch (IOException e) {
            throw new ApplicationException(String.format("Can't open connection to url %s", url));
        }
    }

    @Override
    protected String getContentType() {
        return urlConnection.getContentType();
    }

    @Override
    protected void copyContent() throws Exception {
        Files.copy(urlConnection.getInputStream(), getTempPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), url);
    }

    @Override
    protected void deleteTempResources() throws Exception {
    }
}

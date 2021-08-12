package com.demidrolll.myphotos.common.model;

import com.demidrolll.myphotos.exception.ApplicationException;
import com.demidrolll.myphotos.exception.ValidationException;
import com.demidrolll.myphotos.model.ImageResource;

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractMimeTypeImageResource implements ImageResource {

    private Path tempPath;

    protected abstract String getContentType();

    protected String getExtension() {
        String contentType = getContentType();
        if ("image/jpeg".equalsIgnoreCase(contentType)) {
            return "jpg";
        } else if ("image/png".equalsIgnoreCase(contentType)) {
            return "png";
        } else {
            throw new ValidationException("Only JPEG and PNG formats supported. Current format is " + contentType);
        }
    }

    protected abstract void copyContent() throws Exception;

    @Override
    public Path getTempPath() {
        if (tempPath == null) {
            tempPath = TempFileFactory.createTempFile(getExtension());
            try {
                copyContent();
            } catch (Exception e) {
                throw new ApplicationException(String.format("Can't copy content from %s to %s", toString(), tempPath), e);
            }
        }
        return tempPath;
    }

    @Override
    public abstract String toString();

    protected abstract void deleteTempResources() throws Exception;

    @Override
    public void close() {
        TempFileFactory.deleteTempFile(tempPath);
        try {
            deleteTempResources();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, "Can't delete resources from {0}", toString());
        }
    }
}

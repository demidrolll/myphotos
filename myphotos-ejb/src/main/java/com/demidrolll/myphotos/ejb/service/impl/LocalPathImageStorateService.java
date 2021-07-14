package com.demidrolll.myphotos.ejb.service.impl;

import com.demidrolll.myphotos.common.annotation.cdi.Property;
import com.demidrolll.myphotos.common.config.ImageCategory;
import com.demidrolll.myphotos.ejb.service.FileNameGeneratorService;
import com.demidrolll.myphotos.ejb.service.ImageStorageService;
import com.demidrolll.myphotos.exception.ApplicationException;
import com.demidrolll.myphotos.model.OriginalImage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class LocalPathImageStorateService implements ImageStorageService {

    @Inject
    private Logger logger;

    @Inject
    @Property("myphotos.storage.root.dir")
    private String storageRoot;

    @Inject
    @Property("myphotos.media.absolute.dir")
    private String mediaRoot;

    @Inject
    private FileNameGeneratorService fileNameGeneratorService;

    @Override
    public String saveProtectedImage(Path path) {
        String fileName = fileNameGeneratorService.generateUniqueFileName();
        Path destinationPath = Paths.get(storageRoot + fileName);
        saveImage(path, destinationPath);
        return fileName;
    }

    private void saveImage(Path sourcePath, Path destinationPath) {
        try {
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException | RuntimeException e) {
            logger.log(Level.WARNING, String.format("Move failed from %s to %s. Try to copy...", sourcePath, destinationPath), e);
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                ApplicationException applicationException = new ApplicationException("Can't save image: " + destinationPath, e);
                applicationException.addSuppressed(ex);
                throw applicationException;
            }
        }
        logger.log(Level.INFO, "Saved image: {0}", destinationPath);
    }

    @Override
    public String savePublicImage(ImageCategory imageCategory, Path path) {
        String fileName = fileNameGeneratorService.generateUniqueFileName();
        Path destinationPath = Paths.get(mediaRoot + imageCategory.getRelativeRoot() + fileName);
        saveImage(path, destinationPath);
        return "/" + imageCategory.getRelativeRoot() + fileName;
    }

    @Override
    public void deletePublicImage(String url) {
        Path destinationPath = Paths.get(mediaRoot + url.substring(1));
        try {
            Files.deleteIfExists(destinationPath);
        } catch (IOException | RuntimeException e) {
            logger.log(Level.SEVERE, String.format("Delete public image failed: %s", destinationPath), e);
        }
    }

    @Override
    public OriginalImage getOriginalImage(String originalUrl) {
        Path originalPath = Paths.get(storageRoot + originalUrl);
        try {
            return new OriginalImage(
                    Files.newInputStream(originalPath),
                    Files.size(originalPath),
                    originalPath.getFileName().toString()
            );
        } catch (IOException e) {
            throw new ApplicationException(String.format("Can't get access to original iamge: %s", originalPath), e);
        }
    }
}

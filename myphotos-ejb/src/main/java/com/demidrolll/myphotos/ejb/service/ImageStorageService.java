package com.demidrolll.myphotos.ejb.service;

import com.demidrolll.myphotos.common.config.ImageCategory;
import com.demidrolll.myphotos.model.OriginalImage;

import java.nio.file.Path;

public interface ImageStorageService {

    String saveProtectedImage(Path path);

    String savePublicImage(ImageCategory imageCategory, Path path);

    void deletePublicImage(String url);

    OriginalImage getOriginalImage(String originalUrl);
}

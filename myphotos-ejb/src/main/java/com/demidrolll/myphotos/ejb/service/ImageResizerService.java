package com.demidrolll.myphotos.ejb.service;

import com.demidrolll.myphotos.common.config.ImageCategory;

import java.nio.file.Path;

public interface ImageResizerService {

    void resize(Path sourcePath, Path destinationPath, ImageCategory imageCategory);
}

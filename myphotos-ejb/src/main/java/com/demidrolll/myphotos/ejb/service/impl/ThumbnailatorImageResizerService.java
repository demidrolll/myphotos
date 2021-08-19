package com.demidrolll.myphotos.ejb.service.impl;

import com.demidrolll.myphotos.common.config.ImageCategory;
import com.demidrolll.myphotos.ejb.service.ImageResizerService;
import com.demidrolll.myphotos.exception.ApplicationException;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@ApplicationScoped
public class ThumbnailatorImageResizerService implements ImageResizerService {

    @Override
    public void resize(Path sourcePath, Path destinationPath, ImageCategory imageCategory) {
        Thumbnails.Builder<File> builder = Thumbnails.of(sourcePath.toFile());
        if (imageCategory.isCrop()) {
            builder.crop(Positions.CENTER);
        }
        try {
            builder
                    .size(imageCategory.getWidth(), imageCategory.getHeight())
                    .outputFormat(imageCategory.getOutputFormat())
                    .outputQuality(imageCategory.getQuality())
                    .allowOverwrite(true)
                    .toFile(destinationPath.toFile());
        } catch (IOException e) {
            throw new ApplicationException(String.format("Can't resize image: %s", e.getMessage()), e);
        }
    }
}

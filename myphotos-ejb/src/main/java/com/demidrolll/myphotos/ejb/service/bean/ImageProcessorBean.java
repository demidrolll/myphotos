package com.demidrolll.myphotos.ejb.service.bean;

import com.demidrolll.myphotos.common.config.ImageCategory;
import com.demidrolll.myphotos.common.model.TempImageResource;
import com.demidrolll.myphotos.ejb.service.ImageResizerService;
import com.demidrolll.myphotos.ejb.service.ImageStorageService;
import com.demidrolll.myphotos.ejb.service.interceptor.ImageResourceInterceptor;
import com.demidrolll.myphotos.model.ImageResource;
import com.demidrolll.myphotos.model.domain.Photo;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ImageProcessorBean {

    @Inject
    private ImageResizerService imageResizerService;

    @Inject
    private ImageStorageService imageStorageService;

    @Interceptors({ImageResourceInterceptor.class})
    public String processProfileAvatar(ImageResource imageResource) {
        return createResizedImage(imageResource, ImageCategory.PROFILE_AVATAR);
    }

    @Interceptors({ImageResourceInterceptor.class})
    public Photo processPhoto(ImageResource imageResource) {
        Photo photo = new Photo();
        photo.setLargeUrl(createResizedImage(imageResource, ImageCategory.LARGE_PHOTO));
        photo.setSmallUrl(createResizedImage(imageResource, ImageCategory.SMALL_PHOTO));
        photo.setOriginalUrl(imageStorageService.saveProtectedImage(imageResource.getTempPath()));

        return photo;
    }

    private String createResizedImage(ImageResource imageResource, ImageCategory imageCategory) {
        try (TempImageResource tempPath = new TempImageResource()) {
            imageResizerService.resize(imageResource.getTempPath(), tempPath.getTempPath(), imageCategory);
            return imageStorageService.savePublicImage(imageCategory, tempPath.getTempPath());
        }
    }
}

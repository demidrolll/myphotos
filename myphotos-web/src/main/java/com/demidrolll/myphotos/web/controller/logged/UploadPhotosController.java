package com.demidrolll.myphotos.web.controller.logged;

import com.demidrolll.myphotos.common.config.Constants;
import com.demidrolll.myphotos.model.AsyncOperation;
import com.demidrolll.myphotos.model.Pageable;
import com.demidrolll.myphotos.model.domain.Photo;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.PhotoService;
import com.demidrolll.myphotos.web.model.PartImageResource;
import com.demidrolll.myphotos.web.security.SecurityUtils;
import com.demidrolll.myphotos.web.util.RoutingUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;

import static com.demidrolll.myphotos.web.Constants.PHOTO_LIMIT;

@WebServlet(urlPatterns = "/upload-photos", asyncSupported = true, loadOnStartup = 1)
@MultipartConfig(maxFileSize = Constants.MAX_UPLOADED_PHOTO_SIZE_IN_BYTES)
public class UploadPhotosController extends AbstractUploadController<Photo> {

    @Inject
    private PhotoService photoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Profile profile = SecurityUtils.getCurrentProfile();
        List<Photo> photos = photoService.findProfilePhotos(profile.getId(), new Pageable(1, PHOTO_LIMIT - 1));
        req.setAttribute("profile", profile);
        req.setAttribute("photos", photos);
        req.setAttribute("profilePhotos", Boolean.TRUE);
        RoutingUtils.forwardToPage("upload-photos", req, resp);
    }

    @Override
    protected Map<String, String> getResultMap(Photo photo, HttpServletRequest request) {
        return Map.of(
                "smallUrl", photo.getSmallUrl(),
                "largeUrl", photo.getLargeUrl(),
                "originalUrl", photo.getOriginalUrl(),
                "views", String.valueOf(photo.getViews()),
                "downloads", String.valueOf(photo.getDownloads()),
                "created", DateFormat.getDateInstance(DateFormat.SHORT, request.getLocale()).format(photo.getCreated())
        );
    }

    @Override
    protected void uploadImage(Profile profile, PartImageResource partImageResource, AsyncOperation<Photo> asyncOperation) {
        photoService.uploadNewPhoto(profile, partImageResource, asyncOperation);
    }
}

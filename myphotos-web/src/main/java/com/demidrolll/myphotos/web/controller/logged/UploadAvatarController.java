package com.demidrolll.myphotos.web.controller.logged;

import com.demidrolll.myphotos.common.config.Constants;
import com.demidrolll.myphotos.model.AsyncOperation;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.ProfileService;
import com.demidrolll.myphotos.web.model.PartImageResource;

import javax.inject.Inject;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@WebServlet(urlPatterns = "/upload-avatar", asyncSupported = true, loadOnStartup = 1)
@MultipartConfig(maxFileSize = Constants.MAX_UPLOADED_PHOTO_SIZE_IN_BYTES)
public class UploadAvatarController extends AbstractUploadController<Profile> {

    @Inject
    private ProfileService profileService;

    @Override
    protected Map<String, String> getResultMap(Profile result, HttpServletRequest request) {
        return Map.of("thumbnailUrl", result.getAvatarUrl());
    }

    @Override
    protected void uploadImage(Profile profile, PartImageResource partImageResource, AsyncOperation<Profile> asyncOperation) {
        profileService.uploadNewAvatar(profile, partImageResource, asyncOperation);
    }
}

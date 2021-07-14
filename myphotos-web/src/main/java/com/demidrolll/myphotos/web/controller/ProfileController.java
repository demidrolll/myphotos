package com.demidrolll.myphotos.web.controller;

import com.demidrolll.myphotos.model.Pageable;
import com.demidrolll.myphotos.model.SortMode;
import com.demidrolll.myphotos.model.domain.Photo;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.service.PhotoService;
import com.demidrolll.myphotos.service.ProfileService;
import com.demidrolll.myphotos.web.util.RoutingUtils;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.demidrolll.myphotos.web.Constants.PHOTO_LIMIT;

@WebServlet(value = "/", loadOnStartup = 1)
public class ProfileController extends HttpServlet {

    private final Set<String> homeUrls = Set.of("/");

    @EJB
    private ProfileService profileService;

    @EJB
    private PhotoService photoService;

    @Inject
    protected Logger logger;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.log(Level.INFO, "Start ProfileController");
        String url = req.getRequestURI();
        if (isHomeUrl(url)) {
            handleHomeRequest(req, resp);
        } else {
            handleProfileRequest(req, resp);
        }
    }

    private void handleProfileRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uid = req.getRequestURI().substring(1);
        Profile profile = profileService.findByUid(uid);
        req.setAttribute("profile", profile);
        req.setAttribute("profilePhotos", Boolean.TRUE);
        List<Photo> photos = photoService.findProfilePhotos(profile.getId(), new Pageable(1, PHOTO_LIMIT));
        req.setAttribute("photos", photos);
        RoutingUtils.forwardToPage("profile", req, resp);
    }

    private void handleHomeRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SortMode sortMode = Optional.ofNullable(req.getParameter("sort"))
                .map(SortMode::of)
                .orElse(SortMode.POPULAR_PHOTO);
        List<Photo> photos = photoService.findPopularPhotos(sortMode, new Pageable(1, PHOTO_LIMIT));
        req.setAttribute("photos", photos);
        long totalCount = photoService.countAllPhotos();
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("sortMode", sortMode.name().toLowerCase());
        RoutingUtils.forwardToPage("home", req, resp);
    }

    private boolean isHomeUrl(String url) {
        return homeUrls.contains(url);
    }
}

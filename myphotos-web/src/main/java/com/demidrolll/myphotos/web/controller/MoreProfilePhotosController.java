package com.demidrolll.myphotos.web.controller;

import com.demidrolll.myphotos.model.Pageable;
import com.demidrolll.myphotos.model.domain.Photo;
import com.demidrolll.myphotos.service.PhotoService;
import com.demidrolll.myphotos.web.util.RoutingUtils;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.demidrolll.myphotos.web.Constants.PHOTO_LIMIT;

@WebServlet(urlPatterns = "/photos/profile/more", loadOnStartup = 1)
public class MoreProfilePhotosController extends HttpServlet {

    @EJB
    private PhotoService photoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long profileId = Long.parseLong(req.getParameter("profileId"));
        int page = Integer.parseInt(req.getParameter("page"));
        List<Photo> photos = photoService.findProfilePhotos(profileId, new Pageable(page, PHOTO_LIMIT));
        req.setAttribute("photos", photos);
        req.setAttribute("profilePhotos", Boolean.TRUE);
        RoutingUtils.forwardToFragment("more-photos", req, resp);
    }
}

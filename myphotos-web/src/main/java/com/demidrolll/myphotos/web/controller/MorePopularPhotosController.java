package com.demidrolll.myphotos.web.controller;

import com.demidrolll.myphotos.model.Pageable;
import com.demidrolll.myphotos.model.SortMode;
import com.demidrolll.myphotos.model.domain.Photo;
import com.demidrolll.myphotos.service.PhotoService;
import com.demidrolll.myphotos.web.util.RoutingUtils;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.demidrolll.myphotos.web.Constants.PHOTO_LIMIT;

@WebServlet(urlPatterns = "/photos/popular/more", loadOnStartup = 1)
public class MorePopularPhotosController extends HttpServlet {

    @EJB
    private PhotoService photoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SortMode sortMode = SortMode.of(req.getParameter("sort"));
        int page = Integer.parseInt(req.getParameter("page"));
        List<Photo> photos = photoService.findPopularPhotos(sortMode, new Pageable(page, PHOTO_LIMIT));
        req.setAttribute("photos", photos);
        req.setAttribute("sortMode", sortMode.name().toLowerCase());
        RoutingUtils.forwardToFragment("more-photos", req, resp);
    }
}

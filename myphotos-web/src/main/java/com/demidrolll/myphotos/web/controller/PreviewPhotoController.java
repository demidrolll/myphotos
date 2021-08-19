package com.demidrolll.myphotos.web.controller;

import com.demidrolll.myphotos.service.PhotoService;
import com.demidrolll.myphotos.web.util.RoutingUtils;
import com.demidrolll.myphotos.web.util.UrlExtractorUtil;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/preview/*", loadOnStartup = 1)
public class PreviewPhotoController extends HttpServlet {

    @EJB
    private PhotoService photoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long photoId = Long.parseLong(UrlExtractorUtil.getPathVariableValue(req.getRequestURI(), "/preview/", ".jpg"));
        String redirectUrl = photoService.viewLargePhoto(photoId);
        RoutingUtils.redirectToUri(redirectUrl, req, resp);
    }
}

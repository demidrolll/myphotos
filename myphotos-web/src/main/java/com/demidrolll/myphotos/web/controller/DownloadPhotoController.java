package com.demidrolll.myphotos.web.controller;

import com.demidrolll.myphotos.model.OriginalImage;
import com.demidrolll.myphotos.service.PhotoService;
import com.demidrolll.myphotos.web.util.UrlExtractorUtil;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(urlPatterns = "/download/*", loadOnStartup = 1)
public class DownloadPhotoController extends HttpServlet {

    @EJB
    private PhotoService photoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long photoId = Long.parseLong(UrlExtractorUtil.getPathVariableValue(req.getRequestURI(), "/download/", ".jpg"));
        OriginalImage originalImage = photoService.downloadOriginalImage(photoId);

        resp.setHeader("Content-Disposition", "attachment; filename=" + originalImage.getName());
        resp.setContentType(getServletContext().getMimeType(originalImage.getName()));
        resp.setContentLengthLong(originalImage.getSize());
        try (InputStream in = originalImage.getIn();
             OutputStream out = resp.getOutputStream()) {
            in.transferTo(out);
        }
    }
}

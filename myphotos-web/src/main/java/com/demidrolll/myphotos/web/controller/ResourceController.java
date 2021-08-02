package com.demidrolll.myphotos.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(urlPatterns = {"/static/*", "/favicon.ico"}, loadOnStartup = 1)
public class ResourceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path path = Paths.get(getServletContext().getRealPath("/"), req.getRequestURI());
        resp.setContentType(getServletContext().getMimeType(path.toString()));
        resp.setContentLengthLong(Files.size(path));
        try (OutputStream out = resp.getOutputStream()) {
            Files.copy(path, out);
        }
    }
}

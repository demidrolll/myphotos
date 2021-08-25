package com.demidrolll.myphotos.web.util;

import com.demidrolll.myphotos.web.security.SecurityUtils;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public final class RoutingUtils {

    public static void forwardToPage(String pageName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("currentPage", String.format("../view/%s.jsp", pageName));
        request.getRequestDispatcher("/WEB-INF/template/page-template.jsp").forward(request, response);
    }

    public static void forwardToFragment(String fragmentName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(String.format("/WEB-INF/fragment/%s.jsp", fragmentName)).forward(request, response);
    }

    public static void redirectToUri(String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(url);
    }

    public static void redirectToValidAuthUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (SecurityUtils.isTempAuthenticated()) {
            redirectToUri("/sign-up", request, response);
        } else {
            redirectToUri("/" + SecurityUtils.getCurrentProfile().getUid(), request, response);
        }
    }

    public static void sendJson(JsonObject json, HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendJson("application/json", json, request, response);
    }

    public static void sendFileUploaderJson(JsonObject json, HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendJson("text/plain", json, request, response);
    }

    private static void sendJson(String contentType, JsonObject json, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String content = json.toString();
        int length = content.getBytes(StandardCharsets.UTF_8).length;
        response.setContentType(contentType);
        response.setContentLength(length);
        try (Writer wr = response.getWriter()) {
            wr.write(content);
            wr.flush();
        }
    }
}

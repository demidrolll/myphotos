package com.demidrolll.myphotos.web.controller;

import com.demidrolll.myphotos.web.util.RoutingUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(value = "/generate", loadOnStartup = 1)
public class DataGeneratorController extends HttpServlet {

    //@Inject
    //private DataGeneratorService dataGeneratorService;

    @Inject
    private Logger log;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //dataGeneratorService.generate();
            req.setAttribute("resultMessage", "Done");
        } catch (Exception exception) {
            log.log(Level.WARNING, "Data generation error", exception);
            req.setAttribute("resultMessage", ExceptionUtils.getStackTrace(exception));
        }
        RoutingUtils.forwardToPage("datagenerator", req, resp);
    }
}

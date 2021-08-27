package com.demidrolll.myphotos.web.filter;

import com.demidrolll.myphotos.exception.BusinessException;
import com.demidrolll.myphotos.web.component.ExceptionConverter;
import com.demidrolll.myphotos.web.component.HttpStatusException;
import com.demidrolll.myphotos.web.model.ErrorModel;
import com.demidrolll.myphotos.web.util.RoutingUtils;
import com.demidrolll.myphotos.web.util.WebUtils;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.demidrolll.myphotos.web.Constants.EMPTY_MESSAGE;

@WebFilter(filterName = "ErrorFilter", asyncSupported = true)
public class ErrorFilter extends AbstractFilter {

    @Inject
    private Logger logger;

    @Inject
    private ExceptionConverter exceptionConverter;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, new ThrowExceptionInstedOfSendErrorResponse(response));
        } catch (Throwable th) {
            Throwable throwable = unWrapThrowable(th);
            logError(request, throwable);
            ErrorModel errorModel = exceptionConverter.convertToHttpStatus(throwable);
            handleError(request, errorModel, response);
        }
    }

    private void handleError(HttpServletRequest request, ErrorModel errorModel, HttpServletResponse response) throws IOException, ServletException {
        response.reset();
        response.setStatus(errorModel.getStatus());
        if (WebUtils.isAjaxRequest(request)) {
            sendAjaxJsonErrorResponse(errorModel, request, response);
        } else {
            sendErrorPage(errorModel, request, response);
        }
    }

    private void sendErrorPage(ErrorModel errorModel, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errorModel", errorModel);
        RoutingUtils.forwardToPage("error", request, response);
    }

    private void sendAjaxJsonErrorResponse(ErrorModel errorModel, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = Json.createObjectBuilder()
                .add("success", false)
                .add("error", errorModel.getMessage())
                .build();
        RoutingUtils.sendJson(json, request, response);
    }

    private void logError(HttpServletRequest request, Throwable th) {
        String errorMessage = String.format("Can't process request: %s -> %s", request.getRequestURI(), th.getMessage());
        if (th instanceof BusinessException) {
            logger.log(Level.WARNING, errorMessage);
        } else {
            logger.log(Level.SEVERE, errorMessage, th);
        }
    }

    private Throwable unWrapThrowable(Throwable th) {
        if (th instanceof ServletException && th.getCause() != null) {
            return unWrapThrowable(th.getCause());
        } else if (th instanceof EJBException && th.getCause() != null) {
            return unWrapThrowable(th.getCause());
        } else {
            return th;
        }
    }

    private static class ThrowExceptionInstedOfSendErrorResponse extends HttpServletResponseWrapper {

        public ThrowExceptionInstedOfSendErrorResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            throw new HttpStatusException(sc, msg);
        }

        @Override
        public void sendError(int sc) throws IOException {
            throw new HttpStatusException(sc, EMPTY_MESSAGE);
        }
    }
}

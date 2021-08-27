package com.demidrolll.myphotos.web.component;

import com.demidrolll.myphotos.exception.AccessForbiddenException;
import com.demidrolll.myphotos.exception.InvalidAccessTokenException;
import com.demidrolll.myphotos.exception.ObjectNotFoundException;
import com.demidrolll.myphotos.exception.ValidationException;

import javax.enterprise.context.Dependent;
import javax.ws.rs.Produces;
import java.util.Map;

import static com.demidrolll.myphotos.web.Constants.DEFAULT_ERROR_MESSAGE;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Dependent
public class ExceptionMappingProducer {

    @Produces
    public Map<Class<? extends Throwable>, Integer> getExceptionToStatusCodeMapping() {
        return Map.of(
                ObjectNotFoundException.class, SC_NOT_FOUND,
                ValidationException.class, SC_BAD_REQUEST,
                AccessForbiddenException.class, SC_FORBIDDEN,
                InvalidAccessTokenException.class, SC_UNAUTHORIZED
        );
    }

    @Produces
    public Map<Integer, String> getStatusMessagesMapping() {
        return Map.of(
                SC_BAD_REQUEST, "Current request is invalid",
                SC_UNAUTHORIZED, "Authentication required",
                SC_FORBIDDEN, "Requested operation not permitted",
                SC_NOT_FOUND, "Requested resource not found",
                SC_INTERNAL_SERVER_ERROR, DEFAULT_ERROR_MESSAGE
        );
    }
}

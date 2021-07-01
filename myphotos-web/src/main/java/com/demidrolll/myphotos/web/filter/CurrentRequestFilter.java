package com.demidrolll.myphotos.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "CurrentRequestFilter", asyncSupported = true)
public class CurrentRequestFilter extends AbstractFilter {

    public static final String CURRENT_REQUEST_URL = "currentRequestUrl";

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute(CURRENT_REQUEST_URL, request.getRequestURI());
        chain.doFilter(request, response);
    }
}

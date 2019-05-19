package com.danarossa.filters;

import com.danarossa.controllers.AuthorisationController;
import com.danarossa.controllers.ControllerException;
import com.danarossa.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {

    AuthorisationController authorisationController = new AuthorisationController();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty())
                throw new ControllerException("Token is null");
            User user = authorisationController.getUser(request);
            filterChain.doFilter(request, servletResponse);
        } catch (ControllerException e) {
            String path = request.getPathInfo();
            if (path.equals("/login")) {
                authorisationController.login(request, (HttpServletResponse) servletResponse);
            } else if (path.equals("/register")) {
                authorisationController.register(request, (HttpServletResponse) servletResponse);
            }
        }

    }

    @Override
    public void destroy() {

    }
}

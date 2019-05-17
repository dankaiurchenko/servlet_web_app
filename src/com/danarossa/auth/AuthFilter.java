package com.danarossa.auth;

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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("inside of filter  ");
        System.out.println(((HttpServletRequest) servletRequest).getPathInfo());
        try {
            String token = request.getHeader("Authorization");
            if (token == null || token.isEmpty())
                throw new ControllerException("Token is null");
            System.out.println("token" + token);
            User user = authorisationController.getUser(request);
            filterChain.doFilter(request, servletResponse);
        } catch (ControllerException e) {
            System.out.println(e.getMessage());
            String path = request.getPathInfo();
            if (path.equals("/login")) {
                authorisationController.login(request, (HttpServletResponse) servletResponse);
            } else if (path.equals("/register")) {
                authorisationController.register(request, (HttpServletResponse) servletResponse);
            }
        }

    }
}

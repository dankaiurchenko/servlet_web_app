package com.danarossa.auth;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String token = request.getHeader("token");
        if(token != null && !token.isEmpty()){
            filterChain.doFilter(request, servletResponse);
        } else {
            String path = request.getPathInfo();
            if(path.equals("/login")){

            }else if(path.equals("/register")){

            }
        }
    }
}

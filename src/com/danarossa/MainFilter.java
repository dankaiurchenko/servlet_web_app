package com.danarossa;

import javax.servlet.*;
import java.io.IOException;

public class MainFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        String path = req.getRequestURI();
//        String topfolder = path.substring(1);
//        if (topfolder.contains("/")) {
//            topfolder = topfolder.substring(0, topfolder.indexOf("/"));
//        }
//
//        if (topfolder.startsWith("_")) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else if (topfolder.endsWith(":")) {
//            servletRequest.getRequestDispatcher(path.replaceFirst(":", "")).
//                    forward(servletRequest, servletResponse);
//        } else {
//            servletRequest.getRequestDispatcher("/index.php" + path).
//                    forward(servletRequest, servletResponse);
//        }

    }
}
